package cz.tyckouni.poopio.core.dao;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import cz.tyckouni.poopio.base.entities.Poop;

public interface PoopsDao {

    void create(Poop poop, FirebaseUser user, DatabaseReference.CompletionListener listener);

    void delete(Poop poop, FirebaseUser user, DatabaseReference.CompletionListener listener);
}
