package com.trade.api.share.service;

import com.trade.api.dto.BuyOrSell;
import com.trade.api.entity.Share;
import com.trade.api.share.exception.ShareNotFoundException;
import com.trade.api.share.jpa.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;

    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    @Override
    public int getAvailableQuantity(String shareName) {
        Share share = shareRepository.findById(shareName).orElseThrow(() -> new ShareNotFoundException(shareName));
        return share.getAvailableQuantity();
    }

    @Override
    public void updateQuantity(String shareName, int quantity, BuyOrSell buyOrSell) {
        Share share = shareRepository.findById(shareName).orElseThrow(() -> new ShareNotFoundException(shareName));
        if (buyOrSell == BuyOrSell.BUY) {
            share.setAvailableQuantity(share.getAvailableQuantity() - quantity);
        } else {
            share.setAvailableQuantity(share.getAvailableQuantity() + quantity);
        }
        shareRepository.save(share);
    }
}
