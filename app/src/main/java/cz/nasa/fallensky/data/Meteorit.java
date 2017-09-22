package cz.nasa.fallensky.data;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
@JsonObject
public class Meteorit extends RealmObject  {
	public Meteorit() {
	}
	@PrimaryKey
	@JsonField
	public Integer id;
	@JsonField
	public Integer mass;

	@JsonField
	public String name;

	@JsonField
	public String nametype;


	@JsonField
	public String recclass;


	@JsonField
	public double reclat;

	@JsonField
	public double reclong;


	@JsonField
	public String year;


	@JsonField
	public String fall;


}
