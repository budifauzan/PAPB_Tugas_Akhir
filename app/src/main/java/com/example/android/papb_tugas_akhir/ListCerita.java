package com.example.android.papb_tugas_akhir;

/**
 * Created by Alex on 12/5/2018.
 */

public class ListCerita {
    private String title;
    private String overview;

    public ListCerita(String title, String overview) {
        this.title = title;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}