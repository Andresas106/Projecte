using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using MySQL_BD;
using Windows.UI;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaCuina.ControlsPersonalitzats
{
    public sealed partial class ComandaUI : UserControl
    {
        public ComandaUI()
        {
            this.InitializeComponent();
        }



        public ComandaDB comanda
        {
            get { return (ComandaDB)GetValue(comandaProperty); }
            set { SetValue(comandaProperty, value); }
        }

        // Using a DependencyProperty as the backing store for comanda.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty comandaProperty =
            DependencyProperty.Register("comanda", typeof(ComandaDB), typeof(ComandaUI), new PropertyMetadata(new ComandaDB()));

        private void ListView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            if(lstLiniesComanda.SelectedIndex!= -1)
            {
                LiniaComandaDB linia = (LiniaComandaDB)lstLiniesComanda.SelectedValue;
                linia.Update();
            }
        }
    }
}
