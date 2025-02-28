package org.hegglandtech.mccontrol.utils;

import java.security.SecureRandom;

public class Token {

    private final org.bukkit.entity.Player player;
//    private final long expireAt;

    public Token(org.bukkit.entity.Player player) {
        this.player = player;
    }

    public Token() {
        this.player = null;
    }

    public String generate(int length) {
        String code = generateCode(length);

        if (this.player != null) {
            ServerLogger.print("token generated: " + code + ". Command issued by " + this.player.getName());
            player.sendMessage("token generated, see console for token");
        } else {
            ServerLogger.print("token generated: " + code + ". Command issued by console");
        }

        return code;
    }

    private String generateCode(int length) {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(8);

        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }

        return code.toString();
    }
}
