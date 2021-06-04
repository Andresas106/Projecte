
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace MySQL_BD
{
    public class DBMySQL : DbContext
    {
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseMySQL("Server=51.68.224.27;Database=dam2_averdes;UID=dam2-averdes;Password=47110598C");
            //optionsBuilder.UseMySQL("Server=localhost;Database=projecte;UID=root;Password=");
        }
    }
}
