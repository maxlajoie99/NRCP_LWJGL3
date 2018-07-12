/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.cidco.lwjgl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//ca.cidco.mavenlwjgl//LWJGL//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "LWJGLTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "ca.cidco.mavenlwjgl.LWJGLTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LWJGLAction",
        preferredID = "LWJGLTopComponent"
)
@Messages({
    "CTL_LWJGLAction=LWJGL",
    "CTL_LWJGLTopComponent=LWJGL Window",
    "HINT_LWJGLTopComponent=This is a LWJGL window"
})
public final class LWJGLTopComponent extends TopComponent{

    static final Logger LOG = Logger.getLogger(LWJGLTopComponent.class.getName());
    private LWJGLPanel p;
    
    public LWJGLTopComponent() {
        initComponents();
        setName(Bundle.CTL_LWJGLTopComponent());
        setToolTipText(Bundle.HINT_LWJGLTopComponent());
        
        setMinimumSize(new Dimension(0,0));
        setLayout(new BorderLayout());
        
        p = new LWJGLPanel();
        add(p, BorderLayout.CENTER);
        
        this.setVisible(true);
        this.transferFocus();
        
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                System.out.println(e.getKeyCode());
                getPanel().getCanvas().moveCamera(e.getKeyCode());
                repaint();
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            
            private Integer lastX = null;
            private Integer lastY = null;
            
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastX != null && lastY != null){
                    float sensitivity = 0.5f;
                    
                    float offsetX = (e.getX() - lastX) * sensitivity;
                    float offsetY = (lastY - e.getY()) * sensitivity;
                    
                    getPanel().getCanvas().rotateCamera(offsetX, offsetY);
                    lastX = e.getX();
                    lastY = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });
        
        p.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
                
        p.setSize(this.getSize());
        p.render();
        if (p.getImage() != null)
            g.drawImage(p.getImage(), 0, 0, null);
    }
    
    public LWJGLPanel getPanel(){
        return p;
    }
    
    @Override
    public void componentOpened() {   
        
    }

    @Override
    public void componentClosed() {

    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
