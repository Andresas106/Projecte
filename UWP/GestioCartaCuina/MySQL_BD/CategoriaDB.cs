using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Text;

namespace MySQL_BD
{
    public class CategoriaDB
    {

        private int codi;
        private String nom;
        private String color;
        public CategoriaDB() { }

        public CategoriaDB(int codi, String nom, String color)
        {
            this.Codi = codi;
            this.Nom = nom;
            this.Color = color;
        }

        public int Codi { get => codi; set => codi = value; }
        public string Nom { get => nom; set => nom = value; }
        public string Color { get => color; set => color = value; }

        public static List<CategoriaDB> getCategories()
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
                            consulta.CommandText = "select c.codi, c.nom, c.color from Categoria c";

                            DbDataReader reader = consulta.ExecuteReader();

                            List<CategoriaDB> categories = new List<CategoriaDB>();
                            
                            while (reader.Read())
                            {
                                int codi = reader.GetInt32(reader.GetOrdinal("codi"));
                                String nom = reader.GetString(reader.GetOrdinal("nom"));
                                String color = reader.GetString(reader.GetOrdinal("color"));


                                CategoriaDB c = new CategoriaDB(codi, nom, color);
                                categories.Add(c);
                            }
                            return categories;
                        }
                    }
                }
            }
            catch (Exception)
            {

            }
            return null;
        }
    }
}
