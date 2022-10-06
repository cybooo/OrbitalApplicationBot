package com.github.cybooo.orbital.commands;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

public class PingCommand extends ApplicationCommand {

    @JDASlashCommand(
            scope = CommandScope.GLOBAL,
            name = "ping",
            description = "Displays the bot's ping"
    )
    public void execute(GlobalSlashEvent event) {
        event.deferReply().queue();

        final long gatewayPing = event.getJDA().getGatewayPing();
        event.getJDA().getRestPing()
                .queue(restPing -> event.getHook()
                        .sendMessageFormat("Gateway ping: **%d ms**\nRest ping: **%d ms**", gatewayPing, restPing)
                        .queue());
    }
}