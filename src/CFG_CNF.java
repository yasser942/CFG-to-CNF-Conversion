import java.util.*;

public class CFG_CNF {
    private String converterInput;
  
    private String epsilon = "";

    private Map<String, List<String>> grammarMap = new LinkedHashMap<>();

    public void assignInputToConverter(String converterInput) {
        this.converterInput = converterInput;
    }
    public void printLanguage() {

        for (Map.Entry<String, List<String>> entry : grammarMap.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.print(key + " - ");

            for (int i = 0; i < value.size(); i++) {
                System.out.print(value.get(i));
                if (i!=value.size() - 1){
                    System.out.print(" | ");
                }

            }

            System.out.println();
        }
        System.out.println(" ");
    }
    public void storeInMap() {

    String[] splitedInput = converterInput.split("\\n");
    for (String line : splitedInput) {
        String[] tempString = line.split("-|\\|");
        String variable = tempString[0].trim();

        String[] production = Arrays.copyOfRange(tempString, 1, tempString.length);
        List<String> productionList = new ArrayList<>();

        // trim the empty space
        for (int i = 0; i < production.length; i++) {
            production[i] = production[i].trim();
        }

        // import array into ArrayList
        for (String s : production) {
            productionList.add(s);
        }

        //insert element into map
        grammarMap.put(variable, productionList);
    }
}
    public void eliminateEpsilon() {

        Iterator itr = grammarMap.entrySet().iterator();
        Iterator itr2 = grammarMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.contains("€")) {
                if (productionRow.size() > 1) {
                    productionRow.remove("€");
                    epsilon = entry.getKey().toString();


                } else {

                    epsilon = entry.getKey().toString();
                    grammarMap.remove(epsilon);
                }
            }
        }

        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {
                    if (epsilon.equals(Character.toString(productionList.get(i).charAt(j)))) {

                        if (temp.length() == 2) {

                            temp = temp.replace(epsilon, "");

                            if (!grammarMap.get(entry.getKey().toString()).contains(temp)) {
                                grammarMap.get(entry.getKey().toString()).add(temp);
                            }

                        } else if (temp.length() == 3) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!grammarMap.get(entry.getKey().toString()).contains(deletedTemp)) {
                                grammarMap.get(entry.getKey().toString()).add(deletedTemp);
                            }

                        } else if (temp.length() == 4) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!grammarMap.get(entry.getKey().toString()).contains(deletedTemp)) {
                                grammarMap.get(entry.getKey().toString()).add(deletedTemp);
                            }
                        } else {

                            if (!grammarMap.get(entry.getKey().toString()).contains("€")) {
                                grammarMap.get(entry.getKey().toString()).add("€");
                            }
                        }
                    }
                }
            }
        }
    }
    public void removeDuplicateKeyValue() {
        System.out.println("2- Remove Duplicate Key Value: ");

        grammarMap.forEach((key, value) -> {
            ArrayList<String> productionRow = (ArrayList<String>) value;
            productionRow.removeIf(s -> s.contains(key.toString()));
        });

        printLanguage();
    }
     public void eliminateSingleVariable() {
    grammarMap.forEach((key, value) -> {
        Set<String> keySet = grammarMap.keySet();
        ArrayList<String> productionList = (ArrayList<String>) value;
        for (int i = 0; i < productionList.size(); i++) {
            String temp = productionList.get(i);
            if (keySet.contains(temp)) {
                List<String> productionValue = grammarMap.get(temp);
                productionList.remove(temp);
                grammarMap.get(key).addAll(productionValue);
            }
        }
    });
}
    private boolean checkDuplicateInProductionList(Map<String, List<String>> map, String key) {
        boolean notFound = true;
        outerloop:
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            List<String> productionList = entry.getValue();
            for (String s : productionList) {
                if (productionList.size() < 2) {
                    if (s.equals(key)) {
                        notFound = false;
                        break outerloop;
                    } else {
                        notFound = true;
                    }
                }
            }
        }
        return notFound;
    }
    public void assignVariable() {

        System.out.println("4- Assign new variable for two non-terminal or one terminal: ");

        Iterator itr5 = grammarMap.entrySet().iterator();
        String key = null;
        int asciiBegin = 71; //G

        Map<String, List<String>> tempList = new LinkedHashMap<>();

        while (itr5.hasNext()) {

            Map.Entry entry = (Map.Entry) itr5.next();
            Set set = grammarMap.keySet();

            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();
            Boolean found1 = false;
            Boolean found2 = false;
            Boolean found = false;


            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    if (temp.length() == 3) {

                        String newProduction = temp.substring(1, 3); // SA

                        if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(grammarMap, newProduction)) {
                            found = true;
                        } else {
                            found = false;
                        }

                        if (found) {
                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction);
                            key = String.valueOf((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }


                    } else if (temp.length() == 2) {

                        for (int k = 0; k < keySet.size(); k++) {

                            if (!keySet.get(k).equals(Character.toString(productionList.get(i).charAt(j)))) { // if substring not equals to keySet
                                found = false;

                            } else {
                                found = true;
                                break;
                            }

                        }

                        if (!found) {
                            String newProduction = Character.toString(productionList.get(i).charAt(j));

                            if (checkDuplicateInProductionList(tempList, newProduction) && checkDuplicateInProductionList(grammarMap, newProduction)) {

                                ArrayList<String> newVariable = new ArrayList<>();
                                newVariable.add(newProduction);
                                key = Character.toString((char) asciiBegin);

                                tempList.put(key, newVariable);

                                asciiBegin++;

                            }
                        }
                    } else if (temp.length() == 4) {

                        String newProduction1 = temp.substring(0, 2); // SA
                        String newProduction2 = temp.substring(2, 4); // SA

                        if (checkDuplicateInProductionList(tempList, newProduction1) && checkDuplicateInProductionList(grammarMap, newProduction1)) {
                            found1 = true;
                        } else {
                            found1 = false;
                        }

                        if (checkDuplicateInProductionList(tempList, newProduction2) && checkDuplicateInProductionList(grammarMap, newProduction2)) {
                            found2 = true;
                        } else {
                            found2 = false;
                        }


                        if (found1) {

                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction1);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }

                        if (found2) {
                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction2);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }
                    }
                }
            }
        }
        grammarMap.putAll(tempList);
        printLanguage();
    }
    public void removeThreeTerminal() {

        Iterator itr = grammarMap.entrySet().iterator();
        ArrayList<String> keyList = new ArrayList<>();
        Iterator itr2 = grammarMap.entrySet().iterator();

        // obtain key that use to eliminate two terminal and above
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.size() < 2) {
                keyList.add(entry.getKey().toString());
            }
        }

        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            if (productionList.size() > 1) {
                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    for (int j = 0; j < temp.length(); j++) {

                        if (temp.length() > 2) {
                            String stringToBeReplaced1 = temp.substring(j, temp.length());
                            String stringToBeReplaced2 = temp.substring(0, temp.length() - j);

                            for (String key : keyList) {
                                List<String> keyValues = grammarMap.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];

                                if (stringToBeReplaced1.equals(value)) {
                                    grammarMap.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced1, key);

                                    if (!grammarMap.get(entry.getKey().toString()).contains(temp)) {
                                        grammarMap.get(entry.getKey().toString()).add(i, temp);
                                    }
                                } else if (stringToBeReplaced2.equals(value)) {
                                    grammarMap.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced2, key);

                                    if (!grammarMap.get(entry.getKey().toString()).contains(temp)) {
                                        grammarMap.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }

                        } else if (temp.length() == 2) {

                            for (String key : keyList) {
                                List<String> keyValues = grammarMap.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];

                                for (int pos = 0; pos < temp.length(); pos++) {
                                    String tempChar = String.valueOf(temp.charAt(pos));

                                    if (value.equals(tempChar)) {
                                        grammarMap.get(entry.getKey()).remove(temp);
                                        temp = temp.replace(tempChar, key);
                                        grammarMap.get(entry.getKey()).add(i, temp);
                                    }
                                }
                            }

                        }

                    }
                }
            } else if (productionList.size() == 1) {

                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    if (temp.length() == 2) {
                        for (String key : keyList) {
                            List<String> keyValues = grammarMap.get(key);
                            String[] values = keyValues.toArray(new String[keyValues.size()]);
                            String value = values[0];

                            for (int pos = 0; pos < temp.length(); pos++) {
                                String tempChar = String.valueOf(temp.charAt(pos));

                                if (value.equals(tempChar)) {
                                    grammarMap.get(entry.getKey()).remove(temp);
                                    temp = temp.replace(tempChar, key);
                                    grammarMap.get(entry.getKey()).add(i, temp);
                                }
                            }
                        }
                    }
                }

            }
        }
    }








}
