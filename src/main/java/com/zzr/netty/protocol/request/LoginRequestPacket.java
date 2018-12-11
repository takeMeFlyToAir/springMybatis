package com.zzr.netty.protocol.request;

import com.zzr.netty.protocol.Packet;
import lombok.Data;

import static com.zzr.netty.protocol.command.Command.LOGIN_REQUEST;


@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
