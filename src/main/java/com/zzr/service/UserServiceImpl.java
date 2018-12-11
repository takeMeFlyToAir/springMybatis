package com.zzr.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaozhirong on 2018/12/4.
 */
@Service
public class UserServiceImpl implements UserService {
    public List<String> findAllUser() {
        return Arrays.asList("kermit", "gonzo", "fozzie");
    }
}
