using MySQL_BD;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Imaging;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Media.Imaging;
using Windows.UI.Xaml.Navigation;

// La plantilla de elemento Control de usuario está documentada en https://go.microsoft.com/fwlink/?LinkId=234236

namespace GestioCartaCuina.ControlsPersonalitzats
{
    public sealed partial class PlatUI : UserControl
    {
        public PlatUI()
        {
            this.InitializeComponent();
        }


        


        public PlatDB plat
        {
            get { return (PlatDB)GetValue(platProperty); }
            set { SetValue(platProperty, value); }
        }

        // Using a DependencyProperty as the backing store for plat.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty platProperty =
            DependencyProperty.Register("plat", typeof(PlatDB), typeof(PlatUI), new PropertyMetadata(new PlatDB()));

        
    }
}
