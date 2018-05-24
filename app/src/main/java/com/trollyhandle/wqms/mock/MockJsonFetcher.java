package com.trollyhandle.wqms.mock;

import com.trollyhandle.wqms.network.RawJsonFetcher;

public class MockJsonFetcher extends RawJsonFetcher {
    private static final String mockDataUrl =
            "http://130.157.3.112/ceistudent1/tutorial/montenek/default.php";

    public MockJsonFetcher() {
        super(mockDataUrl);
    }


}
