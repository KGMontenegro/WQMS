package com.nederlonder.wqms.network;

public class MockJsonFetcher extends RawJsonFetcher {
    private static final String mockDataUrl =
            "http://130.157.3.112/ceistudent1/tutorial/montenek/default.php";

    public MockJsonFetcher() {
        super(mockDataUrl);
    }


}
