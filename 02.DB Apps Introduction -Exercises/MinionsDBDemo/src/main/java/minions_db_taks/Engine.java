package minions_db_taks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Engine implements Runnable{
    public static final String MINIONS_TABLE="minions";
    public static final String TOWNS_TABLE="towns";
    public static final String VILLAINS_TABLE="villains";
    public static final String MINIONS_VILLAINS_TABLE="minions_villains";

    private Connection connection;
    private Scanner scanner;

    public Engine(Connection connection) {

        this.connection=connection;
        this.scanner=new Scanner(System.in);
    }

    public void run() {
        try {
            // Enter number between 2 and 9 and Start selected task!!
            //!!! For some task have to enter addition input
            this.callTask(this.scanner.nextInt());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void callTask(int taskNumber) throws SQLException{

        switch (taskNumber){
            case 2:
                this.getVillainsNames();
                break;
            case 3:
                this.getMinionsName();
                break;
            case 4:
                this.addMinion();
                break;
            case 5:
                this.changeTownNameToUpperCase();
                break;
            case 6:
                //TODO
                this.removeVillain();
                break;
            case 7:
                this.printOrderedMinionNames();
                break;
            case 8:
                this.increaseMinionsAge();
                break;

            case 9:
                this.increaseMinionsAgeStoreProcedure();
                break;
            default:
                break;
        }
    }




    //Task2
    private void getVillainsNames() throws SQLException {
        String query = "select v.name,count(v2.minion_id) from villains as v join minions_villains v2 on v.id = v2.villain_id group by v.name having count(v2.minion_id) >? order by count(v2.minion_id) desc";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.setInt(1, 15);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(String.format("%s %d",
                    resultSet.getString(1),
                    resultSet.getInt(2)));
        }
    }

    //Task3
    //!!!Enter villain id
    private void getMinionsName() throws SQLException {
        this.scanner = new Scanner(System.in);

        String query = "SELECT \n" +
                "    v.id,\n" +
                "    v.name,\n" +
                "    v.evilness_factor,\n" +
                "    m.id,\n" +
                "    m.name,\n" +
                "    m.age,\n" +
                "    m.town_id\n" +
                "FROM\n" +
                "    villains AS v\n" +
                "        LEFT JOIN\n" +
                "    minions_villains AS mv ON mv.villain_id = v.id\n" +
                "        LEFT JOIN\n" +
                "    minions AS m ON mv.minion_id = m.id\n" +
                "WHERE\n" +
                "    v.id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        int id = Integer.parseInt(scanner.nextLine());
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        int count = 0;
        while (resultSet.next()) {
            if (count == 0) {
                System.out.println("Villain: " + resultSet.getString("name"));
                count++;
            }
            String nameOfMinion = resultSet.getString("m.name");
            String ageOfMinion = resultSet.getString("m.age");
            if (nameOfMinion == null || ageOfMinion == null) {
                continue;
            }
            System.out.println(String.format("%d. %s %s", count++, nameOfMinion, ageOfMinion));
        }
        if (count == 0) {
            System.out.println(String.format("No villain with ID %d exists in the database.", id));
        }
    }

    //Task4
    // !!!Enter two rows for minion and for villain
    // Exam: Minion: Bob 14 Berlin
    //       Villain: Gru

    private void addMinion()throws SQLException{
        //Enter input
        this.scanner = new Scanner(System.in);
        String[] minionsParams = scanner.nextLine().split("\\s+");
        String[] vilainParams = scanner.nextLine().split("\\s+");

        String minionName=minionsParams[1];
        int minionAge=Integer.parseInt(minionsParams[2]);
        String townName= minionsParams[3];
        String villainName=vilainParams[1];

        //Check town  is exist into DB - if not add into db
         if (!this.isExistEntity(townName,TOWNS_TABLE)){
             this.insertTown(townName);

             }

        //Check villain is exist into DB - if not add to db and set default evilness factor of “evil”

        if(!this.isExistEntity(villainName,VILLAINS_TABLE)){
             this.insertVillain(villainName);
        }
        //Add minion into DB -  set town and set minions to serve of the villain
         int townId= getId(townName,TOWNS_TABLE);
         int villainId=getId(villainName,VILLAINS_TABLE);

         this.insertMinion(minionName,minionAge,townId);

         int minionId=getId(minionName,MINIONS_TABLE);
         this.insertMinionsVillains(minionId,minionName,villainId,villainName);

        // TODO Update DB only if all operations are succsefully done

    }
    //Task5
    //!!!Enter country name
    private void changeTownNameToUpperCase()throws SQLException{
        this.scanner = new Scanner(System.in);

        String countryNamne=this.scanner.nextLine();
        String query=String.format("Select name from towns where country = '%s'",countryNamne);
        PreparedStatement preparedStatement =this.connection.prepareStatement(query);

        ResultSet resultSet= preparedStatement.executeQuery();
        List<String > townNames= new ArrayList<String>();
        while (resultSet.next()){

            String name = resultSet.getString(1).toUpperCase();
            townNames.add(name);

        }

        if (!townNames.isEmpty()){

            System.out.println(townNames.size()+"town names were affected.\n"+townNames.toString());
        }else{
            System.out.println("No town names were affected.");
        }



    }

    //Task6 ------- TODO
    //Enter villain ID
    private void removeVillain()throws SQLException {
        //Enter input
        this.scanner = new Scanner(System.in);
        int villainID = Integer.parseInt(scanner.nextLine());
            //check is exist villain with that id
            if(this.isExistEntityByID(villainID,"id",VILLAINS_TABLE)){
                int deleteMinions=0;
                int deleteVillain=0;
                String villainName=this.getNameByID(villainID,VILLAINS_TABLE);
                //check is villain has served minions
                if (this.isExistEntityByID(villainID,"villain_id",MINIONS_VILLAINS_TABLE)){
                    //releases his miniins
                    //List<Integer> villainMinions=this.getListByID(villainID,"villain_id",MINIONS_VILLAINS_TABLE);
                  deleteMinions = this.deleteTableRows(villainID,MINIONS_VILLAINS_TABLE);

                }

                //delete villain with that id
              deleteVillain = this.deleteTableRows(villainID,VILLAINS_TABLE);

                System.out.println(villainName +" was deleted");
                System.out.println(deleteMinions +" was deleted");

            }
            else {
                System.out.println("No such villain was found");
            }

        }



    //Task7
    private void printOrderedMinionNames() throws SQLException{


        List<String> minionNames = getList(MINIONS_TABLE);
        for (int i=0;i<minionNames.size()/2;i++){

            System.out.println(minionNames.get(i));
            System.out.println(minionNames.get(minionNames.size()-1-i));

            }

    }

    //Task8
    //Enter list ot the minion IDs (2 1 4)
    private void increaseMinionsAge() throws SQLException {

        //Enter input
        this.scanner = new Scanner(System.in);
        String[] minionIDs = scanner.nextLine().split("\\s+");
        //Update rows
        for (int i=0; i < minionIDs.length;i++){
            int id = Integer.parseInt(minionIDs[i]);
            this.updateRowByID(id,MINIONS_TABLE);

        }
        // Print after update
        this.printTableInfo(MINIONS_TABLE);
    }


    //Task9
    // Enter  minion_ID
    private void increaseMinionsAgeStoreProcedure( ) throws SQLException {
        //Enter input
        this.scanner = new Scanner(System.in);
        String[] minionIDs = scanner.nextLine().split("\\s+");
        //Update rows
        for (int i=0; i < minionIDs.length;i++){
            int id = Integer.parseInt(minionIDs[i]);
            CallableStatement increaseAgeStatement = this.connection.prepareCall("{CALL usp_get_older(?)}");
            increaseAgeStatement.setInt(1, id);
            increaseAgeStatement.execute();
        }
        // Print after update
        this.printTableInfo(MINIONS_TABLE);

    }

    //----------------------------Helper methods---------------------------------
    private int getId(String columName,String tableName)throws SQLException {
        int result =0;

        String query=String.format("Select id from %s where name = '%s'",tableName,columName);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        result= resultSet.getInt(1);



        return result;

    }

    private boolean isExistEntity(String entity,String tableName) throws SQLException{
        boolean result =false;

        String townQuery= String.format("SELECT * FROM %s WHERE name LIKE '%s'", tableName, entity);
        PreparedStatement preparedStatement= this.connection.prepareStatement(townQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            result=true;
        }

        return result;

    }


    private boolean isExistEntityByID(int id,String columnName,String tableName) throws SQLException{
        boolean result =false;

        String query= String.format("SELECT * FROM %s WHERE '%s' = %d", tableName,columnName,id);
        PreparedStatement preparedStatement= this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            result=true;
        }

        return result;

    }

    private void insertTown(String townName)throws SQLException{
        String query=String.format("Insert Into towns(name,country) Values('%s', null)",townName);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);

        preparedStatement.execute();

        System.out.println(String.format("Town %s was added to the database.",townName));

    }

    private void insertVillain(String villainName)throws SQLException {

        String query = String .format("Insert into villains (name,evilness_factor) Values('%s','evil')",villainName);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);

        preparedStatement.execute();

        System.out.println(String.format("Villain %s was added to the database.",villainName));

    }

    private void insertMinion(String minionName, int minionAge, int townId) throws SQLException{

        String query = String .format("Insert into minions (name,age,town_id) Values('%s',%d,%d)",minionName,minionAge,townId);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);

        preparedStatement.execute();

        System.out.println(String.format("Minion %s was added to the database.",minionName));

    }

    private void insertMinionsVillains(int minionId,String minionName, int villainId, String villainName) throws SQLException{

        String query = String .format("Insert into minions_villains (minion_id, villain_id) Values(%d,%d)",minionId,villainId);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);

        preparedStatement.execute();

        System.out.println(String.format("Successfully added %s to be minion of %s",minionName,villainName));

    }

    private List<String> getList(String table) throws SQLException {
        List<String> list=new ArrayList<String>();

        String query = String.format("Select * from %s",table);

        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            String element = resultSet.getString(2);
            list.add(element);
        }
        return list;
    }


    private List<Integer> getListByID(int id,String columnName,String table) throws SQLException {
        List<Integer> list=new ArrayList<Integer>();

        String query = String.format("Select * from %s where %s = %d ",table,columnName,id);

        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            int element = resultSet.getInt(columnName);
            list.add(element);
        }
        return list;
    }
    private void updateRowByID(int id, String tableName) throws SQLException {
        //get old minion name and age values
        String nameOld = getNameByID(id,tableName);
        int ageOld=getAgeByID(id,tableName);
        //set new values of the minions name and age
        String nameNew = Character.toLowerCase(nameOld.charAt(0))+ nameOld.substring(1);
        int ageNew=ageOld+1;
        //update row
        String updateQuery=String.format("UPDATE %s SET name = '%s', age = %d where id = %d;",tableName,nameNew,ageNew,id);
        PreparedStatement preparedStatement=this.connection.prepareStatement(updateQuery);
        preparedStatement.execute();



    }

    private String getNameByID(int id,String tableName)throws SQLException {
        String result = "";

        String query=String.format("Select name from %s where id = %d",tableName,id);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        result= resultSet.getString(1);



        return result;

    }

    private int getAgeByID(int id,String tableName)throws SQLException {
        int result =0;

        String query=String.format("Select age from %s where id = %d",tableName,id);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        result= resultSet.getInt(1);



        return result;

    }

    private void printTableInfo(String tableName) throws SQLException {
        String query=String.format("Select * from %s ",tableName);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String name =resultSet.getString("name");
            int age =resultSet.getInt("age");
            System.out.println(name+" "+age);
        }

    }

    private int deleteTableRows(int id,String tableName)throws SQLException{
        String query = String.format("DELETE * FROM %s WHERE id = $d",tableName,id);
        PreparedStatement preparedStatement=this.connection.prepareStatement(query);

          return preparedStatement.executeUpdate();

    }




    }







