package com.sharperture.remotecsv;

import java.util.List;

interface MainActivityCallback {
    void processData(List<String[]> data);
}
