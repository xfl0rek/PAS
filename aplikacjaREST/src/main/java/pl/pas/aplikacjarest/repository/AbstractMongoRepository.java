package pl.pas.aplikacjarest.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.pas.aplikacjarest.model.Admin;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Manager;
import pl.pas.aplikacjarest.model.User;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {
    private ConnectionString connectionString = new ConnectionString(
            "mongodb://mongodbpas1:27017,mongodbpas2:27018,mongodbpas3:27019/?replicaSet=replica_set_single"
    );

    private MongoCredential credential = MongoCredential.createCredential(
            "admin", "admin", "adminpassword".toCharArray()
    );

    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
            PojoCodecProvider.builder()
                    .register(User.class, Client.class, Manager.class, Admin.class)
                    .automatic(true)
                    .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                    .build()
    );

    private MongoClient mongoClient;
    private MongoDatabase base;

    protected void initDBConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.
                                getDefaultCodecRegistry(),
                        pojoCodecRegistry))
                .build();

        mongoClient = MongoClients.create(settings);
        base = mongoClient.getDatabase("hotelpas");
        if (!getDatabase().listCollectionNames().into(new ArrayList<>()).contains("users")) {
            createUserCollection();
        }
        if (!getDatabase().listCollectionNames().into(new ArrayList<>()).contains("rooms")) {
            createRoomCollection();
        }
        if (!getDatabase().listCollectionNames().into(new ArrayList<>()).contains("rents")) {
           createRentCollection();
        }
    }

    private void createUserCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                        $jsonSchema: {
                            bsonType: "object",
                            required: ["_id", "active", "email", "firstname", "lastname", "password", "username"],
                            properties: {
                                _id: {
                                    bsonType: "objectId"
                                },
                                active: {
                                    bsonType: "bool"
                                },
                                email: {
                                    bsonType: "string",
                                    "minLength": 10,
                                    "maxLength": 50
                                },
                                firstname: {
                                    bsonType: "string",
                                    "minLength": 3,
                                    "maxLength": 30
                                },
                                lastname: {
                                    bsonType: "string",
                                    "minLength": 3,
                                    "maxLength": 30
                                },
                                password: {
                                    bsonType: "string",
                                    "minLength": 8,
                                    "maxLength": 100
                                },
                                username: {
                                    bsonType: "string",
                                    "minLength": 5,
                                    "maxLength": 30
                                }
                            }
                        }}
                        """
                )).validationAction(ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);
        getDatabase().createCollection("users", createCollectionOptions);
        getDatabase().getCollection("users").createIndex(Indexes.ascending("username"), new IndexOptions().unique(true));
    }

    private void createRoomCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                        $jsonSchema: {
                            bsonType: "object",
                            required: ["_id", "baseprice", "rented", "roomcapacity", "roomNumber"],
                            properties: {
                                _id: {
                                    bsonType: "objectId"
                                },
                                baseprice: {
                                    bsonType: "int",
                                    "minimum": 100
                                },
                                rented: {
                                    bsonType: "int",
                                    "minimum": 0,
                                    "maximum": 1
                                },
                                roomcapacity: {
                                    bsonType: "int",
                                    "minimum": 1,
                                    "maximum": 5
                                },
                                roomNumber: {
                                    bsonType: "int",
                                    minimum: 1
                                }
                            }
                        }}
                        """
                )).validationAction(ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);
        getDatabase().createCollection("rooms", createCollectionOptions);
    }
    private void createRentCollection() {
        ValidationOptions validationOptions = new ValidationOptions().validator(
                Document.parse("""
                        {
                        $jsonSchema: {
                            bsonType: "object",
                            required: ["_id", "begintime", "client", "room"],
                            properties: {
                                _id: {
                                    bsonType: "objectId"
                                },
                                begintime: {
                                    bsonType: "date"
                                },
                                endtime: {
                                    bsonType: ["date", "null"]
                                },
                                client: {
                                    bsonType: "object"
                                },
                                room: {
                                    bsonType: "object"
                                }
                            }
                        }}
                        """
                )).validationAction(ValidationAction.ERROR);
        CreateCollectionOptions createCollectionOptions = new CreateCollectionOptions()
                .validationOptions(validationOptions);
        getDatabase().createCollection("rents", createCollectionOptions);
    }


    public MongoDatabase getDatabase() {
        return base;
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }
}

