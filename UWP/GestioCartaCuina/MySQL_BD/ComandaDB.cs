using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace MySQL_BD
{
    public class ComandaDB
    {
        private long codi;
        private DateTime data;
        private String cambrer;
        private int taula;
        private List<LiniaComandaDB> plats;

        public long Codi { get => codi; set => codi = value; }
        public DateTime Data { get => data; set => data = value; }
        public string Cambrer { get => cambrer; set => cambrer = value; }
        public int Taula { get => taula; set => taula = value; }
        public List<LiniaComandaDB> Plats { get => plats; set => plats = value; }

        public ComandaDB()
        {

        }

        public ComandaDB(long codi, DateTime data, string cambrer, int taula,  List<LiniaComandaDB> plats)
        {
            this.codi = codi;
            this.data = data;
            this.cambrer = cambrer;
            this.taula = taula;
            this.plats = plats;
        }

        public static List<ComandaDB> GetComandes()
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
                            consulta.CommandText = "select c.codi, c.data, c.taula, cam.nom from comanda c " +
                                "join cambrer cam on cam.codi=c.cambrer order by c.data asc";

                            DbDataReader reader = consulta.ExecuteReader();
                            
                            List<ComandaDB> comandes = new List<ComandaDB>();
                            while (reader.Read())
                            {
                                
                                long codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));
                                int taula = reader.GetInt32(reader.GetOrdinal("taula"));
                                String nom = reader.GetString(reader.GetOrdinal("nom"));
                                List<LiniaComandaDB> plats = new List<LiniaComandaDB>();

                                ComandaDB c = new ComandaDB(codi, data, nom, taula, plats);
                                if (getNUmLIniesComanda(codi) != 0)
                                {
                                    comandes.Add(c);
                                }
                            }
                            return comandes;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }

        public static List<LiniaComandaDB> getPlatsXComanda(long codi)
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
                            consulta.CommandText = "select p.codi, p.nom, lc.num, lc.comanda, lc.quantitat, lc.preparat" +
                                " from liniacomanda lc " +
                                "join plat p on p.codi=lc.comanda where lc.comanda=@codi and lc.preparat=false";

                            DBUtils.crearParametre(consulta, "codi", System.Data.DbType.Int32, codi);

                            DbDataReader reader = consulta.ExecuteReader();

                            List<LiniaComandaDB> plats = new List<LiniaComandaDB>();
                            while (reader.Read())
                            {
                                int codiPlat = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nomPlat = reader.GetString(reader.GetOrdinal("nom"));
                                int num = reader.GetInt32(reader.GetOrdinal("num"));
                                int comanda = reader.GetInt32(reader.GetOrdinal("comanda"));
                                int quantitat = reader.GetInt32(reader.GetOrdinal("quantitat"));
                                bool preparat = reader.GetBoolean(reader.GetOrdinal("preparat"));

                                LiniaComandaDB p = new LiniaComandaDB(comanda, num, quantitat, preparat, codiPlat, nomPlat);
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

        public static int getNUmLIniesComanda(long codiComanda)
        {
            Int32 numeroLiniesPreparades = 0;
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
                            consulta.CommandText = $@"select count(1) from liniacomanda where comanda=@com 
                                                    and preparat=false";

                            DBUtils.crearParametre(consulta, "com", System.Data.DbType.Int32, codiComanda);

                            // B) llançar la consulta
                            var r = consulta.ExecuteScalar();
                            numeroLiniesPreparades = (Int32)((long)r);
                        }
                    }
                }
            }
            catch (Exception ex)
            {


                // Deixar registre al log (coming soon)
            }
            return numeroLiniesPreparades;
        }
    }
}
