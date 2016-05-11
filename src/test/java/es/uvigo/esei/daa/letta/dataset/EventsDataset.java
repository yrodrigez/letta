package es.uvigo.esei.daa.letta.dataset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import es.uvigo.esei.daa.letta.entities.Event;
import es.uvigo.esei.daa.letta.entities.Event.Categories;
import es.uvigo.esei.daa.letta.entities.Image;
import es.uvigo.esei.daa.letta.entities.Image.ExtensionTypes;

public class EventsDataset {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Event[] events() throws ParseException {
		return new Event[]{
			new Event(
				1,
				"Event1",
				"Foo description 1",
				"Ourense",
				formatter.parse("2012-10-01 20:00:00"),
				formatter.parse("2012-10-01 23:00:00"),
				10,
				"user1",
				Categories.films,
				true
			),
			new Event(
				2,
				"Event2",
				"Foo description 2",
				"Verín",
				formatter.parse("2050-10-02 20:00:00"),
				formatter.parse("2050-10-02 23:00:00"),
				9,
				"user1",
				Categories.tvshows,
				true
			),
			new Event(
				3,
				"Event3",
				"Foo description 3",
				"Marín",
				formatter.parse("2050-10-03 20:00:00"),
				formatter.parse("2050-10-03 23:00:00"),
				100,
				"user2",
				Categories.sports,
				true
			),
			new Event(
				4,
				"Event4",
				"Foo description 4",
				"Bueu",
				formatter.parse("2050-10-04 20:00:00"),
				formatter.parse("2050-10-04 23:00:00"),
				8,
				"user2",
				Categories.literature,
				true
			),
			new Event(
				5,
				"Event5",
				"Foo description 5",
				"Castro",
				formatter.parse("2050-10-05 20:00:00"),
				formatter.parse("2050-10-05 23:00:00"),
				15,
				"user2",
				Categories.videogames,
				true
			),
			new Event(
				6,
				"Event6",
				"Foo description 6",
				"Moaña",
				formatter.parse("2050-10-06 20:00:00"),
				formatter.parse("2050-10-06 23:00:00"),
				20,
				"user3",
				Categories.programming,
				true
			),
			new Event(
				7,
				"Event7",
				"Foo description 7",
				"Pontevedra",
				formatter.parse("2050-10-07 20:00:00"),
				formatter.parse("2050-10-07 23:00:00"),
				30,
				"user5",
				Categories.others,
				true
			),
			new Event(
				8,
				"Event8",
				"Foo description 8",
				"Cuntis",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				true
			),
			new Event(
				9,
				"Event9",
				"Foo description 9",
				"Barbadás",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				10,
				"Event10",
				"Foo description 10",
				"Vigo",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				11,
				"Event11",
				"Foo description 11",
				"Ordes",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				12,
				"Event12",
				"Foo description 12",
				"Noia",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				13,
				"Event13",
				"Foo description 13",
				"San Cristóbal",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				14,
				"Event 14",
				"Foo description 14",
				"Rio de Janeiro",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				15,
				"Event 15",
				"Foo description 15",
				"Ons",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			)
		};
	}
	
	public static Event[] popular() throws ParseException{
		return new Event[]{
			new Event(
				2,
				"Event2",
				"Foo description 2",
				"Verín",
				formatter.parse("2050-10-02 20:00:00"),
				formatter.parse("2050-10-02 23:00:00"),
				9,
				"user1",
				Categories.tvshows,
				true
			),
			new Event(
				3,
				"Event3",
				"Foo description 3",
				"Marín",
				formatter.parse("2050-10-03 20:00:00"),
				formatter.parse("2050-10-03 23:00:00"),
				100,
				"user2",
				Categories.sports,
				true
			),
			new Event(
				4,
				"Event4",
				"Foo description 4",
				"Bueu",
				formatter.parse("2050-10-04 20:00:00"),
				formatter.parse("2050-10-04 23:00:00"),
				8,
				"user2",
				Categories.literature,
				true
			),
			new Event(
				5,
				"Event5",
				"Foo description 5",
				"Castro",
				formatter.parse("2050-10-05 20:00:00"),
				formatter.parse("2050-10-05 23:00:00"),
				15,
				"user2",
				Categories.videogames,
				true
			),
			new Event(
				6,
				"Event6",
				"Foo description 6",
				"Moaña",
				formatter.parse("2050-10-06 20:00:00"),
				formatter.parse("2050-10-06 23:00:00"),
				20,
				"user3",
				Categories.programming,
				true
			),
			new Event(
				7,
				"Event7",
				"Foo description 7",
				"Pontevedra",
				formatter.parse("2050-10-07 20:00:00"),
				formatter.parse("2050-10-07 23:00:00"),
				30,
				"user5",
				Categories.others,
				true
			),
			new Event(
				8,
				"Event8",
				"Foo description 8",
				"Cuntis",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				true
			),
			new Event(
				9,
				"Event9",
				"Foo description 9",
				"Barbadás",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				10,
				"Event10",
				"Foo description 10",
				"Vigo",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				11,
				"Event11",
				"Foo description 11",
				"Ordes",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				12,
				"Event12",
				"Foo description 12",
				"Noia",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			),
			new Event(
				13,
				"Event13",
				"Foo description 13",
				"San Cristóbal",
				formatter.parse("2050-10-08 20:00:00"),
				formatter.parse("2050-10-08 23:00:00"),
				18,
				"user5",
				Categories.others,
				false
			)
		};
	}
	
	public static Event[] featured() throws ParseException{
		return new Event[]{
			new Event(
				2,
				"Event2",
				"Foo description 2",
				"Verín",
				formatter.parse("2050-10-02 20:00:00"),
				formatter.parse("2050-10-02 23:00:00"),
				9,
				"user1",
				Categories.tvshows,
				true
			),
			new Event(
				3,
				"Event3",
				"Foo description 3",
				"Marín",
				formatter.parse("2050-10-03 20:00:00"),
				formatter.parse("2050-10-03 23:00:00"),
				100,
				"user2",
				Categories.sports,
				true
			),
			new Event(
				4,
				"Event4",
				"Foo description 4",
				"Bueu",
				formatter.parse("2050-10-04 20:00:00"),
				formatter.parse("2050-10-04 23:00:00"),
				8,
				"user2",
				Categories.literature,
				true
			),
			new Event(
				5,
				"Event5",
				"Foo description 5",
				"Castro",
				formatter.parse("2050-10-05 20:00:00"),
				formatter.parse("2050-10-05 23:00:00"),
				15,
				"user2",
				Categories.videogames,
				true
			),
			new Event(
				6,
				"Event6",
				"Foo description 6",
				"Moaña",
				formatter.parse("2050-10-06 20:00:00"),
				formatter.parse("2050-10-06 23:00:00"),
				20,
				"user3",
				Categories.programming,
				true
			)
		};
	}
	
	public static Event[] search() throws ParseException{
		return new Event[]{new Event(
				2,
				"Event2",
				"Foo description 2",
				"Verín",
				formatter.parse("2050-10-02 20:00:00"),
				formatter.parse("2050-10-02 23:00:00"),
				9,
				"user1",
				Categories.tvshows,
				true
			
			)
		};
	}
	
	public static Event[] assist() throws ParseException {
		return new Event[]{
			new Event(
					3,
					"Event3",
					"Foo description 3",
					"Marín",
					formatter.parse("2050-10-03 20:00:00"),
					formatter.parse("2050-10-03 23:00:00"),
					100,
					"user2",
					Categories.sports,
					true
			)
		};
	}
		
	
	public static Image getExistentImage(){
		return new Image(getExistentImg_ext(),getExistentImg());
	}
	
	public static byte[] getExistentImg(){
		return Base64.getDecoder().decode("iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOkRERTU3NjIzMURDQTExRTA5OTc0Qjg1RkFFNDk0QzI0IiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOkRERTU3NjI0MURDQTExRTA5OTc0Qjg1RkFFNDk0QzI0Ij4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6RERFNTc2MjExRENBMTFFMDk5NzRCODVGQUU0OTRDMjQiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6RERFNTc2MjIxRENBMTFFMDk5NzRCODVGQUU0OTRDMjQiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz5uBdmsAAAAaUlEQVR42mL8//8/AzGAiYFIwAIiGBkZdYFUDg41B4G2LmOAWs0MxOeB+D8a/gnEKmA1SG60waKwHSSBrhAEliIpegLEPLgUSgPxZ6jCKJggNoUgUAHEh5EFcClkA2JddIWMxAY4QIABALOxQPHZh9w+AAAAAElFTkSuQmCC");
	}
	
	public static Image.ExtensionTypes getExistentImg_ext(){
		return Image.ExtensionTypes.png;
	}
	
	public static int getExistentId(){
		return 1;
	}
	
	public static String getExistentDescription(){
		return "Foo description 1";
	}
	
	public static String getExistentTitle(){
		return "Event1";
	}
	
	public static String getExistentPlace(){
		return "Ourense";
	}
	
	public static Date getExistentStart() throws ParseException{
		return formatter.parse("2012-10-01 20:00:00");
	}
	
	public static Date getExistentEnd() throws ParseException{
		return formatter.parse("2012-10-01 23:00:00");
	}
	
	public static int getExistentNum_assistants(){
		return 10;
	}
	

	public static String getExistentUser_id(){
		return "user1";
	}
	
	public static Categories getExistentCategory(){
		return Categories.films;
	}
	public static boolean getExistentHasImage(){
		return true;
	}
	
	public static Event getExistentEvent() throws ParseException{
		return new Event(getExistentId(),
			getExistentTitle(),
			getExistentDescription(),
			getExistentPlace(),
			getExistentStart(),
			getExistentEnd(),
			getExistentNum_assistants(),
			getExistentUser_id(),
			getExistentCategory(),
			getExistentHasImage());
	}
	
	public static int getNonExistentId(){
		return 1000;
	}
	
}
