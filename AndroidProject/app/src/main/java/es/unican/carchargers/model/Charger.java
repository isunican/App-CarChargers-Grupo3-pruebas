package es.unican.carchargers.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A charging station according to the OpenChargeMap API
 * Documentation: https://openchargemap.org/site/develop/api#/operations/get-poi
 *
 * Currently it only includes a sub-set of the complete model returned by OpenChargeMap
 */
@Parcel
public class Charger {
    
    @SerializedName("ID")                   public String id;
    @SerializedName("NumberOfPoints")       public int numberOfPoints;
    @SerializedName("UsageCost")            public String usageCost;
    @SerializedName("OperatorInfo")         public Operator operator;
    @SerializedName("AddressInfo")          public Address address;
    @SerializedName("Connections")          public List<Connection> connections;
    @SerializedName("DateLastVerified")     public String dateLastVerified;

    public Charger() {
        this.operator = new Operator();
        this.address = new Address();
        this.connections = new ArrayList<Connection>();
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, numberOfPoints, usageCost, operator, address, connections);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Charger charger = (Charger) obj;
        return Objects.equals(id, charger.id);
    }
}
