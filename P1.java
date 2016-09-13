import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class Pair{
	int x,y;
	public Pair(int x,int y){
		this.x = x;
		this.y = y;
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
}
class MyComparator implements Comparator<Pair> {
    public int compare(Pair a, Pair b) {
		return (new Integer(a.getx())).compareTo(new Integer(b.getx()));
    }
}
class MyFrame extends JFrame implements ActionListener, MouseListener{
	int V,INF;
	ArrayList<Pair> vertexMapping;
	ArrayList<ArrayList<Pair> > adjList;
	ArrayList<Pair> l;
	JButton b2,b,r;
	TextField t1,t2,t3,t4,t5;
	JLabel l4,l5,l6,l7;
	public MyFrame(){
		V = 0;
		INF = 9999999;
		vertexMapping = new  ArrayList<Pair>();
		adjList = new ArrayList<ArrayList<Pair> >();
		l = new ArrayList<Pair>();
		setTitle("Dijkstra's Algorithm");
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p = new JPanel();
		p.addMouseListener(this);
		Color c = new Color(0,63,0);
		p.setBackground(c);
		setLayout(new FlowLayout());
		p.setPreferredSize( new Dimension( getWidth(), 550 ) );
		add(p);
		JPanel p1 = new JPanel();
		p1.setLayout(null);
		p1.setPreferredSize( new Dimension( getWidth(), 100 ) );
		//p1.setBackground(Color.BLACK);
		JLabel l1,l2,l3;
		l1 = new JLabel("Use Dijkstra's Algorithm: ");
		l2 = new JLabel("Enter Source: ");
		l3 = new JLabel("Enter Sink: ");
		l1.setBounds(getWidth()/2,10,200,10);
		l2.setBounds(20,30,100,10);
		l3.setBounds(300,30,100,10);
		t1 = new TextField(10);
		t2 = new TextField(10);
		t1.setBounds(120,27,100,20);
		t2.setBounds(380,27,100,20);
		p1.add(l1);
		p1.add(l2);
		p1.add(t1);
		p1.add(t2);
		p1.add(l3);
		b = new JButton("Find Shortest Path!");
		b.setBounds(220,65,170,25);
		p1.add(b);
		l4 = new JLabel("ADD EDGE");
		l5 = new JLabel("From:");
		l6 = new JLabel("To:");
		l7 = new JLabel("Weight:");
		t3 = new TextField(10);
		t4 = new TextField(10);
		t5 = new TextField(10);
		l4.setBounds(1150,25,100,10);
		l5.setBounds(930,45,50,20);
		t3.setBounds(980,45,70,20);
		l6.setBounds(1090,45,20,20);
		t4.setBounds(1120,45,70,20);
		l7.setBounds(1225,45,50,20);
		t5.setBounds(1280,45,70,20);
		p1.add(l4);
		p1.add(l5);
		p1.add(t3);
		p1.add(l6);
		p1.add(l7);
		p1.add(t4);
		p1.add(t5);
		b2 = new JButton("ADD EDGE");
		b2.setBounds(1250,70,100,25);
		r = new JButton("Random");
		r.setBounds(110,65,100,25);
		p1.add(r);
		p1.add(b2);
		add(p1);
		b2.addActionListener(this);
		b.addActionListener(this);
		r.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==b2){
			int from = Integer.valueOf(t3.getText());
			int to = Integer.valueOf(t4.getText());
			int weight = Integer.valueOf(t5.getText());
			if(weight<0 || to>vertexMapping.size() || from>vertexMapping.size() || to<0 || from<0){
				t3.setText("INVALID");
				t4.setText("INVALID");
				t5.setText("INVALID");
				return;
			}
			t3.setText("");
			t4.setText("");
			t5.setText("");
			int x1 = vertexMapping.get(from).getx();
			int y1 = vertexMapping.get(from).gety();
			int x2 = vertexMapping.get(to).getx();
			int y2 = vertexMapping.get(to).gety();
			Graphics g= getGraphics();	
			int midx = (x1+x2)/2;
			int midy = (y1+y2)/2;
			g.drawLine(x1,y1,x2,y2);
			g.setColor(new Color(190,255,190));
			g.fillOval(x1-50/2,y1-50/2,50,50);
			g.setColor(Color.BLACK);
			g.drawOval(x1-50/2,y1-50/2,50,50);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString(new Integer(from).toString(),x1-5,y1+5);
			g.setColor(new Color(190,255,190));
			g.fillOval(x2-50/2,y2-50/2,50,50);
			g.setColor(Color.BLACK);
			g.drawOval(x2-50/2,y2-50/2,50,50);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString(new Integer(to).toString(),x2-5,y2+5);	
			g.drawString(new Integer(weight).toString(),midx-5,midy+5);
			adjList.get(from).add(new Pair(to,weight));
			adjList.get(to).add(new Pair(from,weight));
		}else if(e.getSource()==b){
			int source = Integer.valueOf(t1.getText());
			int sink = Integer.valueOf(t2.getText());
			if(source<0 || source>V || sink<0 || sink>V){
				t1.setText("INVALID");
				t2.setText("INVALID");
			}
			shortestPath(source,sink);
		}else if(e.getSource()==r){
			Random r = new Random();
			shortestPath(r.nextInt(V),r.nextInt(V));
		}
	}
	public void mouseClicked(MouseEvent e) {
		//System.out.println(V);
		adjList.add(new ArrayList<Pair>());
		Integer i = new Integer(V);
		int x = e.getX();
		int y = e.getY();
		vertexMapping.add(new Pair(x,y));
		int width =50,height=50;
		String text = i.toString();
		Graphics g= getGraphics();
		g.setColor(new Color(190,255,190));
		g.fillOval(x-width/2,y-height/2,width,height);
		g.setColor(Color.BLACK);
		g.drawOval(x-width/2,y-height/2,width,height);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(text,x-5,y+5);
		V++;
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void invertNode(int vertex){
		Pair p = vertexMapping.get(vertex);
		int x = p.getx();
		int y = p.gety();	
		int width =50,height=50;
		Graphics g= getGraphics();
		g.setColor(Color.BLACK);
		g.fillOval(x-width/2,y-height/2,width,height);
		g.setColor(Color.RED);
		g.drawOval(x-width/2,y-height/2,width,height);
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString((new Integer(vertex)).toString(),x-5,y+5);		
	}
	public void invertEdge(int vertex,int par){
		Graphics g= getGraphics();
		Pair p = vertexMapping.get(vertex);
		int x = p.getx();
		int y = p.gety();
		g.setColor(Color.RED);
		Pair p1 = vertexMapping.get(par);
		int x1 = p1.getx();
		int y1 = p1.gety();
		g.drawLine(x,y,x1,y1);
	}
	public void printParentEdges(int vertex,int[] parent){
		if(parent[vertex]==-1){
			return;
		}
		printParentEdges(parent[vertex],parent);
		invertEdge(vertex,parent[vertex]);		
	}
	public void printParent(int vertex,int[] parent){
		if(parent[vertex]==-1){
			invertNode(vertex);
			return;
		}
		printParent(parent[vertex],parent);
		invertNode(vertex);		
	}
	public void shortestPath(int src,int sink){
		Graphics g= getGraphics();
		int width = 50,height=50;
		int i=0;
		while(i<adjList.size()){
			Iterator itr = adjList.get(i).iterator();
			Pair pcor = vertexMapping.get(i);
			int x = pcor.getx();
			int y = pcor.gety();
			while(itr.hasNext()){
				Pair p = (Pair)itr.next();
				Pair pcor1 = vertexMapping.get(p.getx());
				int x1 = pcor1.getx();
				int y1 = pcor1.gety();
				g.drawLine(x,y,x1,y1);
			}
			i++;
		}
		for(i=0;i<vertexMapping.size();i++){
			Pair p = vertexMapping.get(i);
			int x = p.getx();
			int y = p.gety();
			g.setColor(new Color(190,255,190));
			g.fillOval(x-width/2,y-height/2,width,height);
			g.setColor(Color.BLACK);
			g.drawOval(x-width/2,y-height/2,width,height);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.drawString((new Integer(i)).toString(),x-5,y+5);
		}
		int[] dist = new int[V];
		int[] parent = new int[V];
		boolean[] doneWith = new boolean[V];
		for(i=0;i<V;i++){
			dist[i] = INF;
			doneWith[i] = false;
			parent[i]=-1;
		}
		dist[src]=0;
		Comparator<Pair> comparator = new MyComparator();
		PriorityQueue <Pair>  q = new PriorityQueue <Pair> (comparator); 
		q.add(new Pair(0,src));
		while(q.size()!=0){
			Pair p = q.poll();
			int u = p.gety();
			if(doneWith[u])
				continue;
			doneWith[u] = true;
			Iterator itr = adjList.get(u).iterator();
			while(itr.hasNext()){
				Pair pr = (Pair)itr.next();
				int v = pr.getx();
				int weight = pr.gety();
				if(!doneWith[v] && dist[v]>dist[u]+weight){
					dist[v] = dist[u]+weight;
					parent[v]=u;
					q.add(new Pair(dist[v],v));
				}
			}
		}
		printParentEdges(sink,parent);
		printParent(sink,parent);
	}
}

class P1{
	public static void main(String str[]){
		new MyFrame();
	}
}