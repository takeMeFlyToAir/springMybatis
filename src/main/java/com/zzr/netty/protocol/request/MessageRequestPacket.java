package com.zzr.netty.protocol.request;

import com.zzr.netty.protocol.Packet;
import lombok.Data;

import static com.zzr.netty.protocol.command.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
