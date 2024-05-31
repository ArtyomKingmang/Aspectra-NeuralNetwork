package com.kingmang.aspectra.utils;

import java.util.HashMap;

public class ChangeList {
    public static HashMap<String, String> getChanges() {
        HashMap<String, String> list = new HashMap<>();
        list.put( "you", "i" );
        list.put( "your", "my" );
        list.put( "you'd", "i'd" );
        list.put( "you'll", "i'll" );
        list.put( "you're", "i'm" );
        list.put( "you've", "i've" );
        list.put( "yourself", "myself" );
        list.put( "yours", "mine" );

        list.put( "i", "you" );
        list.put( "me", "you" );
        list.put( "my", "your" );
        list.put( "mine", "yours" );
        list.put( "myself", "yourself" );
        list.put( "i'd", "you'd" );
        list.put( "i'll", "you'll" );
        list.put( "i've", "you've" );
        list.put( "i'm", "you're" );

        list.put( "why", "because" );
        list.put( "because", "why" );

        list.put( "no", "yes" );
        list.put( "yes", "no" );

        list.put( "like", "dislike" );
        list.put( "dislike", "like" );
        list.put( "love", "hate" );
        list.put( "hate", "love" );

        return list;
    }
}
