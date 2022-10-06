package com.github.cybooo.orbital.commands;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class ServerInfoCommand extends ApplicationCommand {

    @JDASlashCommand(
            scope = CommandScope.GUILD,
            name = "serverinfo",
            description = "Displays information about this server."
    )
    public void execute(GlobalSlashEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Server info - " + event.getGuild().getName());
        embedBuilder.addField("Members", String.valueOf(event.getGuild().getMemberCount()), false);
        embedBuilder.addField("Owner", event.getGuild().getOwner().getEffectiveName(), false);
        embedBuilder.addField("ID", event.getGuild().getId(), false);
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
