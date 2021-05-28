using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace MySQL_BD
{
    public class LiniaComandaDB
    {
        private int comanda;
        private int num;
        private int quantitat;
        private bool preparat;
        private int item;

        public LiniaComandaDB() { }

        public LiniaComandaDB(int comanda, int num, int quantitat, bool preparat, int item)
        {
            Comanda = comanda;
            Num = num;
            Quantitat = quantitat;
            Preparat = preparat;
            Item = item;
        }

        public int Comanda { get => comanda; set => comanda = value; }
        public int Num { get => num; set => num = value; }
        public int Quantitat { get => quantitat; set => quantitat = value; }
        public bool Preparat { get => preparat; set => preparat = value; }
        public int Item { get => item; set => item = value; }


        public static int getNumeroComandesPlat(long plat)
        {
            Int32 numeroliniesPlat = 0;
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
                            consulta.CommandText = $@"select count(1) from liniaComanda where item=@plat";

                            DBUtils.crearParametre(consulta, "plat", System.Data.DbType.Int32, plat); 

                            // B) llançar la consulta
                            var r = consulta.ExecuteScalar();
                            numeroliniesPlat = (Int32)((long)r);
                        }
                    }
                }
            }
            catch (Exception ex)
            {


                // Deixar registre al log (coming soon)
            }
            return numeroliniesPlat;
        }
    }
}
