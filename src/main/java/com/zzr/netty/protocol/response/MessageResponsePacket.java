package com.zzr.netty.protocol.response;

import com.zzr.netty.protocol.Packet;
import lombok.Data;

import static com.zzr.netty.protocol.command.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }
}
