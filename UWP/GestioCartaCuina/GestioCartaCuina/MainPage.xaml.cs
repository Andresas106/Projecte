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
using GestioCartaCuina.View;

// La plantilla de elemento Página en blanco está documentada en https://go.microsoft.com/fwlink/?LinkId=402352&clcid=0xc0a

namespace GestioCartaCuina
{
    /// <summary>
    /// Página vacía que se puede usar de forma independiente o a la que se puede navegar dentro de un objeto Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
        }

        private void Option1Button_Checked(object sender, RoutedEventArgs e)
        {
            frmPrincipal.Navigate(typeof(GestioCarta), this); 
        }

        private void Option2Button_Checked(object sender, RoutedEventArgs e)
        {
            frmPrincipal.Navigate(typeof(GestioCuina), this);
        }

        private void HamburgerButton_Click(object sender, RoutedEventArgs e)
        {
            NavigationPane.IsPaneOpen = !NavigationPane.IsPaneOpen;
        }

        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
            frmPrincipal.Navigate(typeof(GestioCarta), this);
        }

        private void Option3_Checked(object sender, RoutedEventArgs e)
        {
            frmPrincipal.Navigate(typeof(InformeCarta), this);
        }
    }
}
