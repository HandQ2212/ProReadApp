package com.example.proreadapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.proreadapp.model.StoryCategoryCrossRef;

import java.util.List;

@Dao
public interface StoryCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrossRef(StoryCategoryCrossRef crossRef);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCrossRefs(List<StoryCategoryCrossRef> crossRefs);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStoryCategoryCrossRef(StoryCategoryCrossRef crossRef);
}
