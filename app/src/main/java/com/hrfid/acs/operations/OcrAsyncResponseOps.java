package com.hrfid.acs.operations;

/**
 * Created by joart.cano on 8/17/15.
 */
public interface OcrAsyncResponseOps {
    void updateResults(Boolean success, String result);

    void displayMessage(String text);
}
