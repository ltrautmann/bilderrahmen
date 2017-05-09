package com.sabel.bilderrahmen.interfaces;

import com.sabel.bilderrahmen.Picture_Properties;

/**
 * Created by Robin on 08.05.2017.
 */
public interface Got_Pictures {
     Picture_Properties getPictureAt(int index);

    int size();
}
