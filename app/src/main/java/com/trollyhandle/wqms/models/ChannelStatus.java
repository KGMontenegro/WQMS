package com.trollyhandle.wqms.models;

import java.util.List;

public class ChannelStatus {

    public ChannelDetails channel;

    public List<EntryDetails> feeds;

    public class ChannelDetails {
        public String name;
        String latitude;
        String longitude;
    }
    public class EntryDetails {
        public String created_at;
        public int entry_id;
        public String status;
    }
}
