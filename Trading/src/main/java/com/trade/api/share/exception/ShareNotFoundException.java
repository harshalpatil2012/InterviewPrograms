package com.trade.api.share.exception;


public class ShareNotFoundException extends RuntimeException {

    private final String shareName;

    public ShareNotFoundException(String shareName) {
        super("Share with name " + shareName + " not found");
        this.shareName = shareName;
    }

    public String getShareName() {
        return shareName;
    }
}
