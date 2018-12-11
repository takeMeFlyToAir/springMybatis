package com.zzr.netty.protocol.response;

import com.zzr.netty.protocol.Packet;
import lombok.Data;

import static com.zzr.netty.protocol.command.Command.LOGIN_RESPONSE;


@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
