package com.example.appcambrer;

import inofmila.info.Model.Taula;

public interface SelectedItemListener {
    void onSelectedItem(Taula t);
    void onLongSelectedItem(Taula t, int idxSeleccionat);
}
