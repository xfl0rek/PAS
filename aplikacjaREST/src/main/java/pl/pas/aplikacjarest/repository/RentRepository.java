package pl.pas.aplikacjarest.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.springframework.stereotype.Repository;
import pl.pas.aplikacjarest.model.Rent;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RentRepository extends AbstractMongoRepository {
    @Override
    public void close() throws Exception {

    }

    public RentRepository() {
        this.initDBConnection();
    }

    public void create(Rent rent) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.insertOne(rent);
    }

    public Rent findByID(long id) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        return collection.find().first();
    }

    public List<Rent> findAll() {
        ArrayList<Rent> rents = new ArrayList<>();
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.find().into(rents);
        return rents;
    }

    public void update(Rent rent) {
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        BasicDBObject update = new BasicDBObject();
        update.put("_id", rent.getId());
        collection.replaceOne(update, rent);
    }

    public void delete(long id) {
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        MongoCollection<Rent> collection = getDatabase().getCollection("rents", Rent.class);
        collection.deleteOne(query);
    }

}
