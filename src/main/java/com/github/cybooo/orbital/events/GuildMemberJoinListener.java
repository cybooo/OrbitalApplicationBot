package com.github.cybooo.orbital.events;

import com.github.cybooo.orbital.OrbitalBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class GuildMemberJoinListener extends ListenerAdapter {

    private final OrbitalBot orbitalBot;

    public GuildMemberJoinListener(OrbitalBot orbitalBot) {
        this.orbitalBot = orbitalBot;
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        var user = event.getUser();

        CompletableFuture.runAsync(() -> {
            try (Connection connection = orbitalBot.getMariaDB().getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO userdata (userid, username) VALUES (?, ?);");
                preparedStatement.setLong(1, user.getIdLong());
                preparedStatement.setString(2, event.getUser().getName() + "#" + user.getDiscriminator());
                preparedStatement.executeQuery();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

    }
}
