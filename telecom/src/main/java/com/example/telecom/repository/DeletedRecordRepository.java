package com.example.telecom.repository;

import com.example.telecom.model.DeletedRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeletedRecordRepository extends MongoRepository <DeletedRecord, String> {

}
