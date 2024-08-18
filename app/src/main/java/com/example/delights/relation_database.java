public abstract class relation_database extends RoomDatabase {

    private static volatile relation_database INSTANCE;

    public abstract relationDAO relationDao();

    public static relation_database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (relation_database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    relation_database.class, "relation_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

