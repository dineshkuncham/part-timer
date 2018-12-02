package com.android.part_timer.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.part_timer.database.entity.GeneralData;

@Dao
public interface GeneralDataDao {

    @Insert
    void insert(GeneralData generalData);

    @Query("select id, twentyFourHour from general_data")
    GeneralData getHourFormat();

    @Update
    void update(GeneralData generalData);

}
