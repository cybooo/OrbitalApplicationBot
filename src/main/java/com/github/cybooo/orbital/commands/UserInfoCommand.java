package com.github.cybooo.orbital.commands;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class UserInfoCommand extends ApplicationCommand {

    @JDASlashCommand(
            scope = CommandScope.GUILD,
            name = "userinfo",
            description = "Displays basic information about you."
    )
    public void execute(GlobalSlashEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Info - " + event.getMember().getEffectiveName());
        embedBuilder.addField("Account created", event.getUser().getTimeCreated().toString(), false);
        embedBuilder.addField("Joined", event.getMember().getTimeJoined().toString(), false);
        embedBuilder.addField("ID", event.getMember().getId(), false);
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
