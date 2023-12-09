package com.trade.api.share.jpa;

import com.trade.api.entity.Share;

import java.util.Optional;

public interface ShareRepository {
    Optional<Share> findById(String shareName);

    Share save(Share share);
}
