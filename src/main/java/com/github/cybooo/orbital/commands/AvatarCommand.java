package com.github.cybooo.orbital.commands;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class AvatarCommand extends ApplicationCommand {

    @JDASlashCommand(
            scope = CommandScope.GUILD,
            name = "avatar",
            description = "Displays your avatar"
    )
    public void execute(GlobalSlashEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Your avatar");
        embedBuilder.setImage(event.getUser().getAvatarUrl() == null ?
                "https://cdn.discordapp.com/embed/avatars/0.png" :
                event.getUser().getAvatarUrl());
        event.replyEmbeds(embedBuilder.build()).queue();
    }
}
