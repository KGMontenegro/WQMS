package com.trollyhandle.wqms.models;

import java.util.List;

public class ChannelFeed {
    public ChannelDetails channel;
    public List<FeedEntry> feeds;

    public String[] getFieldNames() {
        return new String[]{
                channel.field1,
                channel.field2,
                channel.field3,
                channel.field4,
                channel.field5,
                channel.field6
        };
    }

    public class ChannelDetails {
        public int id;
        public String name;
        public String description;
        public String latitude;
        public String longitude;
        public String field1;
        public String field2;
        public String field3;
        public String field4;
        public String field5;
        public String field6;
        public String created_at;
        public String updated_at;
        public int last_entry_id;
    }

    public class FeedEntry {
        public int entry_id;
        public String created_at;
        public String field1;
        public String field2;
        public String field3;
        public String field4;
        public String field5;
        public String field6;
    }
}
