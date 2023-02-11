package hust.itep.quanlynhankhau.service.dao;

import hust.itep.quanlynhankhau.model.Population;

import java.util.*;

public class PopulationDao implements Dao<Population> {
    @Override
    public List<Population> getAll() {
        return null;
    }

    @Override
    public Population get(Long id) {
        return null;
    }

    @Override
    public void save(Population population) {

    }

    @Override
    public void delete(Population population) {

    }

    @Override
    public void update(Population population, HashMap<String, Object> columnMap) {

    }
    /*
    private static final String tableName = "population";
    private static final SortedMap<String, String> attributeMap = new TreeMap<>(Map.ofEntries(
            entry("id", "id"),
            entry("name", "name"),
            entry("gender", "gender"),
            entry("birthdate", "birthdate"),
            entry("phoneNumber", "phone_number"),
            entry("nationality", "nationality"),
            entry("ethnicity", "ethnicity"),
            entry("citizenId", "citizen_id"),
            entry("passportNumber", "passport_number"),
            entry("birthplace", "birthplace"),
            entry("nativePlace", "native_place"),
            entry("occupationWorkplace", "occupation_workplace"),
            entry("permanentAddress", "permanent_address"),
            entry("address", "address")
    ));
    private static final SQLMapper<Population> sqlMapper = new SQLMapper<>(tableName, attributeMap);

    @Override
    public List<Population> getAll() {
        Connection connection = DatabaseConnection.getConnection();
        List<Population> ret = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format("SELECT * FROM %s", tableName)
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Population population = new Population();
                sqlMapper.sqlToObject(resultSet, population);
                ret.add(population);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    @Override
    public Optional<Population> get(Integer id) {
        Optional ret = null;
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format("SELECT * FROM %s WHERE id = ?", tableName)
            );
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Population population = new Population();
                sqlMapper.sqlToObject(resultSet, population);
                ret = Optional.of(population);
            } else {
                ret = Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ret;
    }

    @Override
    public void insert(Population population) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = sqlMapper.objectToSQL(population, connection);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Population population) {
        Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format("DELETE FROM %s WHERE id = ?", tableName)
            );
            preparedStatement.setInt(1, population.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Population population, HashMap<String, Object> columnMap) {
        Connection connection = DatabaseConnection.getConnection();


        try {

        } catch (Exception e) {

        }
    }
     */

}
