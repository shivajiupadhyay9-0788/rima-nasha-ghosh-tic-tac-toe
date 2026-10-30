package com.rimanashaghosh.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(17,24,39), PANEL = Color.rgb(31,41,55);
    private final int GOLD = Color.rgb(244,185,66), MINT = Color.rgb(87,214,170);
    private final int WHITE = Color.rgb(248,250,252), MUTED = Color.rgb(156,163,175);
    private EditText nameX, nameO;
    private Spinner seriesSpinner;
    private TextView scoreX, scoreO, status, roundLabel;
    private Button[][] cells = new Button[3][3];
    private char[][] board = new char[3][3];
    private int winsX=0, winsO=0, draws=0, round=1, target=2;
    private char current='X';
    private boolean finished=false;
    private LinearLayout root, gamePanel;
    private String playerX="Player 1", playerO="Player 2";

    @Override public void onCreate(Bundle saved) {
        super.onCreate(saved);
        showHome();
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); return d;
    }
    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); return l;
    }
    private LinearLayout row() {
        LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.HORIZONTAL); l.setGravity(Gravity.CENTER); return l;
    }
    private void pad(View v, int all) { v.setPadding(all,all,all,all); }
    private LinearLayout.LayoutParams lp(int w, int h) { return new LinearLayout.LayoutParams(w,h); }
    private Button button(String label, int bg, int fg) {
        Button b = new Button(this); b.setText(label); b.setTextColor(fg); b.setTextSize(15);
        b.setAllCaps(false); b.setTypeface(Typeface.DEFAULT, Typeface.BOLD); b.setBackground(shape(bg,24)); return b;
    }
    private void showHome() {
        root = column(); root.setGravity(Gravity.CENTER_HORIZONTAL); root.setPadding(24,28,24,24); root.setBackgroundColor(BG);
        ScrollView scroll = new ScrollView(this); scroll.addView(root); setContentView(scroll);
        TextView icon = text("✦  ✕  ○", 27, GOLD, true); icon.setGravity(Gravity.CENTER); root.addView(icon, lp(-1,55));
        TextView title = text("Rima Nasha Ghosh", 28, WHITE, true); title.setGravity(Gravity.CENTER); root.addView(title);
        TextView subtitle = text("TIC TAC TOE GAME", 14, GOLD, true); subtitle.setLetterSpacing(.18f); subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle, lp(-1,36));
        TextView intro = text("A little friendly competition at home", 14, MUTED, false); intro.setGravity(Gravity.CENTER);
        root.addView(intro, lp(-1,42));
        LinearLayout card = column(); card.setBackground(shape(PANEL,28)); card.setPadding(20,20,20,20);
        LinearLayout.LayoutParams cp = lp(-1,-2); cp.topMargin=22; root.addView(card,cp);
        card.addView(text("PLAYER SETUP",13,GOLD,true));
        nameX = new EditText(this); nameX.setSingleLine(); nameX.setText("Player 1"); nameX.setHint("Enter Player 1 name"); nameX.setTextColor(WHITE); nameX.setHintTextColor(MUTED); nameX.setBackground(shape(Color.rgb(55,65,81),16)); nameX.setPadding(16,12,16,12);
        LinearLayout.LayoutParams ep=lp(-1,54); ep.topMargin=12; card.addView(nameX,ep);
        nameO = new EditText(this); nameO.setSingleLine(); nameO.setText("Player 2"); nameO.setHint("Enter Player 2 name"); nameO.setTextColor(WHITE); nameO.setHintTextColor(MUTED); nameO.setBackground(shape(Color.rgb(55,65,81),16)); nameO.setPadding(16,12,16,12);
        ep=lp(-1,54); ep.topMargin=10; card.addView(nameO,ep);
        TextView series = text("MATCH FORMAT",13,GOLD,true); ep=lp(-1,34); ep.topMargin=18; card.addView(series,ep);
        seriesSpinner = new Spinner(this);
        String[] formats={"Best of 3 rounds","Single round","Best of 5 rounds"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, formats);
        seriesSpinner.setAdapter(adapter); seriesSpinner.setSelection(0);
        seriesSpinner.setBackground(shape(Color.rgb(55,65,81),16)); card.addView(seriesSpinner,lp(-1,52));
        TextView rules = text("Two players • Take turns on one device • Scores stay on this screen until you start a new match",12,MUTED,false);
        rules.setGravity(Gravity.CENTER); rules.setPadding(6,18,6,10); card.addView(rules);
        Button start=button("START PLAYING  →",GOLD,BG); ep=lp(-1,58); ep.topMargin=18; card.addView(start,ep);
        start.setOnClickListener(v -> {
            playerX = clean(nameX.getText().toString(),"Player 1");
            playerO = clean(nameO.getText().toString(),"Player 2");
            int pos=seriesSpinner.getSelectedItemPosition(); target=(pos==1?1:(pos==2?3:2));
            winsX=0; winsO=0; draws=0; round=1; current='X'; resetBoard(); showGame();
        });
        TextView footer=text("Made for family game nights ♥",12,MUTED,false); footer.setGravity(Gravity.CENTER);
        ep=lp(-1,48); ep.topMargin=12; root.addView(footer,ep);
    }
    private String clean(String s,String fallback){ return s.trim().isEmpty()?fallback:s.trim(); }
    private void showGame() {
        root=column(); root.setPadding(20,22,20,20); root.setBackgroundColor(BG);
        ScrollView scroll=new ScrollView(this); scroll.addView(root); setContentView(scroll);
        TextView brand=text("RIMA NASHA GHOSH",12,GOLD,true); brand.setGravity(Gravity.CENTER); root.addView(brand,lp(-1,28));
        TextView heading=text("Tic Tac Toe",29,WHITE,true); heading.setGravity(Gravity.CENTER); root.addView(heading);
        roundLabel=text("ROUND "+round,13,MUTED,true); roundLabel.setGravity(Gravity.CENTER); root.addView(roundLabel,lp(-1,35));
        LinearLayout score=row(); score.setBackground(shape(PANEL,22)); score.setPadding(8,14,8,14);
        root.addView(score,lp(-1,90));
        LinearLayout sx=column(), so=column(); sx.setGravity(Gravity.CENTER); so.setGravity(Gravity.CENTER);
        scoreX=text("0",25,GOLD,true); scoreO=text("0",25,MINT,true);
        sx.addView(text(playerX+"  (X)",13,WHITE,true)); sx.addView(scoreX);
        TextView dash=text("VS",12,MUTED,true); dash.setPadding(15,0,15,0);
        so.addView(text(playerO+"  (O)",13,WHITE,true)); so.addView(scoreO);
        score.addView(sx,new LinearLayout.LayoutParams(0,-1,1)); score.addView(dash); score.addView(so,new LinearLayout.LayoutParams(0,-1,1));
        status=text(playerX+"'s turn  •  X",16,WHITE,true); status.setGravity(Gravity.CENTER); status.setPadding(0,18,0,14); root.addView(status,lp(-1,56));
        gamePanel=column(); gamePanel.setBackground(shape(PANEL,26)); gamePanel.setPadding(10,10,10,10);
        LinearLayout.LayoutParams gp=lp(-1,-2); gp.topMargin=8; root.addView(gamePanel,gp);
        for(int r=0;r<3;r++){
            LinearLayout line=row(); gamePanel.addView(line,lp(-1,100));
            for(int c=0;c<3;c++){
                final int rr=r,cc=c;
                Button cell=button("",Color.rgb(55,65,81),WHITE); cell.setTextSize(36); cell.setMinHeight(0); cell.setMinimumHeight(0);
                LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,88,1); p.setMargins(5,5,5,5); line.addView(cell,p); cells[r][c]=cell;
                cell.setOnClickListener(v -> play(rr,cc));
            }
        }
        LinearLayout actions=row(); LinearLayout.LayoutParams ap=lp(-1,58); ap.topMargin=18; root.addView(actions,ap);
        Button next=button("NEXT ROUND",MINT,BG); actions.addView(next,new LinearLayout.LayoutParams(0,-1,1));
        Button home=button("HOME",Color.rgb(55,65,81),WHITE); LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(0,-1,1); hp.leftMargin=10; actions.addView(home,hp);
        next.setOnClickListener(v -> { if(!finished){ Toast.makeText(this,"Finish this round first!",Toast.LENGTH_SHORT).show(); } else if(matchWon()){ showMatchEnd(); } else { round++; resetBoard(); updateStatus(); roundLabel.setText("ROUND "+round); } });
        home.setOnClickListener(v -> showHome());
        TextView note=text("First move: "+playerX+"  •  X starts every round",12,MUTED,false); note.setGravity(Gravity.CENTER); root.addView(note,lp(-1,42));
        updateScores(); updateStatus();
    }
    private void resetBoard(){ board=new char[3][3]; finished=false; current='X'; }
    private void updateScores(){ if(scoreX!=null){scoreX.setText(""+winsX);scoreO.setText(""+winsO);} }
    private void updateStatus(){
        if(status!=null && !finished) status.setText((current=='X'?playerX:playerO)+"'s turn  •  "+current);
    }
    private void play(int r,int c){
        if(finished || board[r][c]!='\0') return;
        board[r][c]=current; Button b=cells[r][c]; b.setText(""+current); b.setTextColor(current=='X'?GOLD:MINT);
        if(hasWon(current)){ finished=true; if(current=='X') winsX++; else winsO++; updateScores(); status.setText((current=='X'?playerX:playerO)+" wins this round! 🎉"); b.setBackground(shape(Color.rgb(75,85,99),22)); if(matchWon()) status.append("\nMATCH WINNER!"); return; }
        if(full()){ finished=true; draws++; status.setText("It's a draw! 🤝"); return; }
        current=(current=='X'?'O':'X'); updateStatus();
    }
    private boolean hasWon(char p){
        for(int i=0;i<3;i++) if((board[i][0]==p&&board[i][1]==p&&board[i][2]==p)||(board[0][i]==p&&board[1][i]==p&&board[2][i]==p)) return true;
        return (board[0][0]==p&&board[1][1]==p&&board[2][2]==p)||(board[0][2]==p&&board[1][1]==p&&board[2][0]==p);
    }
    private boolean full(){ for(int r=0;r<3;r++)for(int c=0;c<3;c++)if(board[r][c]=='\0')return false; return true; }
    private boolean matchWon(){ return winsX>=target || winsO>=target; }
    private void showMatchEnd(){
        String winner=winsX>winsO?playerX:playerO;
        new android.app.AlertDialog.Builder(this).setTitle("🏆 "+winner+" wins the match!")
          .setMessage(playerX+": "+winsX+"  |  "+playerO+": "+winsO+"  |  Draws: "+draws)
          .setCancelable(false).setPositiveButton("New match",(d,w)->showHome())
          .setNegativeButton("Stay here",(d,w)->{}).show();
    }
}
