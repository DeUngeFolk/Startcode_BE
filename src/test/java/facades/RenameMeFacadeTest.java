package facades;

import dtos.RenameMeDTO;
import entities.RenameMe;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

    class RenameMeFacadeTest {

        private static EntityManagerFactory emf;
        private static RenameMeFacade facade;

        public RenameMeFacadeTest() {
        }

        @BeforeAll
        public static void setUpClass() {
            emf = EMF_Creator.createEntityManagerFactoryForTest();
            facade = RenameMeFacade.getFacadeExample(emf);
        }

        @AfterAll
        public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
        }

        // Setup the DataBase in a known state BEFORE EACH TEST

        @BeforeEach
        public void setUp() {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
                em.persist(new RenameMe(1L, "another text", "another text"));
                em.persist(new RenameMe(2L, "another text", "another text"));
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }

        @AfterEach
        public void tearDown() {
//        Remove any data after each test was run
        }


        @Test
        void create() {
            facade.create(new RenameMeDTO(1L, "ole", "olesen"));
            assertEquals(3, facade.getRenameMeCount());
        }

        @Test
        void getById() {
        }

        @Test
        void getPersonCount() throws Exception {
            assertEquals(2, facade.getRenameMeCount(), "Expects two rows in the database");
        }

        @Test
        void update() {

            RenameMe renameMe = new RenameMe(0L, "email", "lars");
            RenameMeDTO renameMeDTO = facade.create(new RenameMeDTO(renameMe));

            RenameMe renameMe1 = new RenameMe(renameMeDTO.getId(),"email", "lars");


            assertEquals(renameMe1.getDummyStr1(), facade.update(new RenameMeDTO(renameMe1)).getDummyStr1());


        }

        @Test
        void getAll() {
        }
    }

