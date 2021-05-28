using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;
using Windows.UI.Xaml.Media;

namespace MySQL_BD
{
    public class PlatDB
    {
        private long codi;
        private String nom;
        private String descripcioMD;
        private double preu;
        private byte[] foto;
        private bool disponible;
        private int categoria;
        private String urlFoto;
        private ImageSource imatgeSource;

        #region propietats
        public long Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string DescripcioMD { get => descripcioMD; set => descripcioMD = value; }
        public double Preu { get => preu; set => preu = value; }
        public byte[] Foto { get => foto; set => foto = value; }
        public bool Disponible { get => disponible; set => disponible = value; }
        public int Categoria { get => categoria; set => categoria = value; }
        public ImageSource ImatgeSource { get => imatgeSource; set => imatgeSource = value; }
        public string UrlFoto { get => urlFoto; set => urlFoto = value; }
        #endregion

        public PlatDB() { }

        public PlatDB(long codi, String nom, String descripcioMD, double preu, byte[] foto, bool disponible, int categoria)
        {
            this.codi = codi;
            this.nom = nom;
            this.descripcioMD = descripcioMD;
            this.preu = preu;
            this.foto = foto;
            this.disponible = disponible;
            this.categoria = categoria;
        }

        public PlatDB(long codi, String nom, String descripcioMD, double preu, String foto, bool disponible, int categoria)
        {
            this.codi = codi;
            this.nom = nom;
            this.descripcioMD = descripcioMD;
            this.preu = preu;
            this.urlFoto = foto;
            this.disponible = disponible;
            this.categoria = categoria;
        }

        public static List<PlatDB> getPlats()
        {
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            //definir la consulta de teams
                            consulta.CommandText = "select p.codi, p.nom, p.descripcioMD, p.preu, p.foto, p.disponible, p.categoria " +
                                "from Plat p";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<PlatDB> plats = new List<PlatDB>();

                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nom = reader.GetString(reader.GetOrdinal("nom"));
                                String descripcioMD = reader.GetString(reader.GetOrdinal("descripcioMD"));
                                float preu = reader.GetFloat(reader.GetOrdinal("preu"));
                                int midaFoto = getBytesFoto(codi);
                                byte[] foto = new byte[midaFoto];
                                reader.GetBytes(reader.GetOrdinal("foto"), 0, foto, 0, midaFoto);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("disponible"));
                                int cat = reader.GetInt32(reader.GetOrdinal("categoria"));

                                PlatDB p = new PlatDB(codi, nom, descripcioMD, preu, foto, disponible, cat);
                                plats.Add(p);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static int getBytesFoto(int codi)
        {
            Int32 numeroBytes = 0;
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            // A) definir la consulta
                            consulta.CommandText = $@"select octet_length(foto) as midaFoto from plat where codi= @codi";

                            DBUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);
                            // B) llançar la consulta
                            var r = consulta.ExecuteScalar();
                            numeroBytes = (Int32)r;
                        }
                    }
                }
            }
            catch (Exception ex)
            {


                // Deixar registre al log (coming soon)
            }
            return numeroBytes;
        }

        public static List<PlatDB> getPlatsXCategoria(int categoria)
        {
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            //definir la consulta de teams
                            consulta.CommandText = "select p.codi, p.nom, p.descripcioMD, p.preu, p.foto, p.disponible, p.categoria " +
                                "from Plat p where p.categoria=@categoria";

                            DBUtils.crearParametre(consulta, "categoria", System.Data.DbType.Int32, categoria);

                            DbDataReader reader = consulta.ExecuteReader();

                            List<PlatDB> plats = new List<PlatDB>();

                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nom = reader.GetString(reader.GetOrdinal("nom"));
                                String descripcioMD = reader.GetString(reader.GetOrdinal("descripcioMD"));
                                float preu = reader.GetFloat(reader.GetOrdinal("preu"));
                                int midaFoto = getBytesFoto(codi);
                                byte[] foto = new byte[midaFoto];
                                reader.GetBytes(reader.GetOrdinal("foto"), 0, foto, 0, midaFoto);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("disponible"));
                                int cat = reader.GetInt32(reader.GetOrdinal("categoria"));

                                PlatDB p = new PlatDB(codi, nom, descripcioMD, preu, foto, disponible, cat);
                                plats.Add(p);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static List<PlatDB> getPlatsXNom(String nom)
        {
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            //definir la consulta de teams
                            consulta.CommandText = "select p.codi, p.nom, p.descripcioMD, p.preu, p.foto, p.disponible, p.categoria " +
                                "from Plat p where p.nom like @nom";

                            DBUtils.crearParametre(consulta, "nom", System.Data.DbType.String, "%" + nom + "%");

                            DbDataReader reader = consulta.ExecuteReader();

                            List<PlatDB> plats = new List<PlatDB>();

                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nomDB = reader.GetString(reader.GetOrdinal("nom"));
                                String descripcioMD = reader.GetString(reader.GetOrdinal("descripcioMD"));
                                float preu = reader.GetFloat(reader.GetOrdinal("preu"));
                                int midaFoto = getBytesFoto(codi);
                                byte[] foto = new byte[midaFoto];
                                reader.GetBytes(reader.GetOrdinal("foto"), 0, foto, 0, midaFoto);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("disponible"));
                                int cat = reader.GetInt32(reader.GetOrdinal("categoria"));

                                PlatDB p = new PlatDB(codi, nomDB, descripcioMD, preu, foto, disponible, cat);
                                plats.Add(p);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public bool Insert()
        {
            DbTransaction trans = null;
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            // A) definir la consulta
                            consulta.CommandText = $@"insert into plat 
                                                      values(@codi, @nom, @descripcio, @preu, from_base64(@foto), @disponible, @categoria)";

                            DBUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, Codi);
                            DBUtils.crearParametre(consulta, "nom", System.Data.DbType.String, Nom);
                            DBUtils.crearParametre(consulta, "descripcio", System.Data.DbType.String, DescripcioMD);
                            DBUtils.crearParametre(consulta, "preu", System.Data.DbType.Double, Preu);
                            DBUtils.crearParametre(consulta, "foto", System.Data.DbType.String, UrlFoto);
                            DBUtils.crearParametre(consulta, "disponible", System.Data.DbType.Boolean, Disponible);
                            DBUtils.crearParametre(consulta, "categoria", System.Data.DbType.Int32, Categoria);

                            // B) llançar la consulta
                            int filesAfectades = consulta.ExecuteNonQuery();
                            if (filesAfectades != 1)
                            {
                                trans.Rollback();
                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                // Deixar registre al log (coming soon)
            }

            return false;
        }

        public static List<PlatDB> getPlatsXNomICategoria(String nom, int categoria)
        {
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();

                        using (var consulta = connexio.CreateCommand())
                        {
                            //definir la consulta de teams
                            consulta.CommandText = "select p.codi, p.nom, p.descripcioMD, p.preu, p.foto, p.disponible, p.categoria " +
                                "from Plat p where p.nom like @nom and p.categoria=@categoria";

                            DBUtils.crearParametre(consulta, "nom", System.Data.DbType.String, "%" + nom + "%");
                            DBUtils.crearParametre(consulta, "categoria", System.Data.DbType.Int32, categoria);

                            DbDataReader reader = consulta.ExecuteReader();

                            List<PlatDB> plats = new List<PlatDB>();

                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nomDB = reader.GetString(reader.GetOrdinal("nom"));
                                String descripcioMD = reader.GetString(reader.GetOrdinal("descripcioMD"));
                                float preu = reader.GetFloat(reader.GetOrdinal("preu"));
                                int midaFoto = getBytesFoto(codi);
                                byte[] foto = new byte[midaFoto];
                                reader.GetBytes(reader.GetOrdinal("foto"), 0, foto, 0, midaFoto);
                                bool disponible = reader.GetBoolean(reader.GetOrdinal("disponible"));
                                int cat = reader.GetInt32(reader.GetOrdinal("categoria"));

                                PlatDB p = new PlatDB(codi, nomDB, descripcioMD, preu, foto, disponible, cat);
                                plats.Add(p);
                            }
                            return plats;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static int getNumPlats()
        {
            Int32 numPlats = 0;
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            // A) definir la consulta
                            consulta.CommandText = $@"select count(1) from plat";
                            // B) llançar la consulta
                            var r = consulta.ExecuteScalar();
                            numPlats = (Int32)((long)r);
                        }
                    }
                }
            }
            catch (Exception ex)
            {


                // Deixar registre al log (coming soon)
            }
            return numPlats;
        }

        public bool Delete()
        {
            DbTransaction trans = null;
            try
            {
                using (DBMySQL context = new DBMySQL())
                {
                    using (var connexio = context.Database.GetDbConnection())
                    {
                        connexio.Open();
                        using (DbCommand consulta = connexio.CreateCommand())
                        {
                            trans = connexio.BeginTransaction();
                            consulta.Transaction = trans;
                            // A) definir la consulta
                            consulta.CommandText = $@"delete from plat where codi=@codi";

                            DBUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, Codi);

                            // B) llançar la consulta
                            int filesAfectades = consulta.ExecuteNonQuery();
                            if (filesAfectades != 1)
                            {
                                trans.Rollback();
                            }
                            else
                            {
                                trans.Commit();
                                return true;
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                // Deixar registre al log (coming soon)
            }

            return false;
        }


    }
}
