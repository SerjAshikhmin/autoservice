package tests;

public interface JsonTestData {

    String GET_ALL_MASTERS_JSON = "[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Evgeniy\",\n" +
            "        \"category\": 3,\n" +
            "        \"busy\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 2,\n" +
            "        \"name\": \"Alex\",\n" +
            "        \"category\": 2,\n" +
            "        \"busy\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 3,\n" +
            "        \"name\": \"Ivan\",\n" +
            "        \"category\": 5,\n" +
            "        \"busy\": true\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 4,\n" +
            "        \"name\": \"Corey\",\n" +
            "        \"category\": 5,\n" +
            "        \"busy\": false\n" +
            "    }\n" +
            "]";

    String GET_MASTER_BY_ID_JSON = "{\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Evgeniy\",\n" +
            "        \"category\": 3,\n" +
            "        \"busy\": true\n" +
            "    }";

    String ADD_NEW_MASTER_JSON = "{\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"Corey\",\n" +
            "    \"category\": 5,\n" +
            "    \"busy\": false\n" +
            "}";

    String UPDATE_MASTER_JSON = "{\n" +
            "        \"id\": 1,\n" +
            "        \"name\": \"Evgeniy\",\n" +
            "        \"category\": 5,\n" +
            "        \"busy\": false\n" +
            "    }";

}
