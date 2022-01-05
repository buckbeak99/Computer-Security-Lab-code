#include<bits/stdc++.h>
using namespace std;
#define ll long long
vector< pair<char, int> > char_cnt;

void ceaserCipherShift(string s){
	for(int shift = -25; shift <= 25; shift++){
		cout<<shift<<' ';
		for(int i=0;i<s.length();i++){
			char c = tolower(s[i]);
			int x = (int) c + shift ;
			if(x<96) x+=26;
			else if(x>122) x-=26;
			cout<<(char)x;
		}
		cout<<endl;
	}
	
}
// checking character case
string tolower(string s){
	string r = "";
	for(auto c : s) {
		if(c>=97 and c<=122) r += c;
		else if(c>=65 and c<=90) r += (char) (c + 32);
		else r += c;
	}
	return r;
}

void sort(map<char, int>& M)
{
    vector<pair<char, int> > A;
    for (auto& it : M) {
        A.push_back(it);
    }
    sort(A.begin(), A.end(),
    	[] (const auto& lhs, const auto& rhs) {
	    return lhs.second > rhs.second;
		}
    );
    for (auto& it : A) {
        cout << it.first << ' '
             << it.second << endl;
    }
    char_cnt = A;
}

vector<pair<char, double> >freq;

bool fun(pair<char, double>& a,
         pair<char, double>& b)
{
    return a.second > b.second;
}
// alphabet frequencies in enlish characters
void init()
{
	freq.push_back(make_pair('e', 12.22));
	freq.push_back(make_pair('t', 9.67));
	freq.push_back(make_pair('a', 8.05));
	freq.push_back(make_pair('o', 7.63));
	freq.push_back(make_pair('i', 6.28));
	freq.push_back(make_pair('n', 6.95));
	freq.push_back(make_pair('s', 6.02));
	freq.push_back(make_pair('r', 5.29));
	freq.push_back(make_pair('h', 6.62));
	freq.push_back(make_pair('d', 5.10));
	freq.push_back(make_pair('l', 4.08));
	freq.push_back(make_pair('u', 2.92));
	freq.push_back(make_pair('c', 2.23));
	freq.push_back(make_pair('m', 2.33));
	freq.push_back(make_pair('f', 2.14));
	freq.push_back(make_pair('y', 2.04));
	freq.push_back(make_pair('w', 2.60));
	freq.push_back(make_pair('g', 2.30));
	freq.push_back(make_pair('p', 1.66));
	freq.push_back(make_pair('b', 1.67));
	freq.push_back(make_pair('v', 0.82));
	freq.push_back(make_pair('k', 0.95));
	freq.push_back(make_pair('x', 0.11));
	freq.push_back(make_pair('q', 0.06));
	freq.push_back(make_pair('j', 0.19));
	freq.push_back(make_pair('z', 0.06));

	sort(freq.begin(), freq.end(), fun);

	cout<<"freq table: \n";
	for(int i=0;i<freq.size(); i++){
		cout<<freq[i].first<<' '<<freq[i].second<<endl;
	}
	cout<<endl;
}

vector<string>v;
// In our given cipher text, alphabet frequencies will be found here
void takeInput_andFindFrequency()
{
	string s;
	map<string , int>mp2, mp3, mp4, mp5;
	map<char, int> ch_mp;
	while(cin>>s){
		s = tolower(s);
		if(s.length()==2) mp2[s]++;
		if(s.length()==3) mp3[s]++;
		if(s.length()==4) mp4[s]++;
		if(s.length()==5) mp5[s]++;
		for(auto c : s){
			ch_mp[c]++;
		}
		v.push_back(s);
	}
	sort(ch_mp);
}
int main()
{
    freopen("input2a.txt","r", stdin);
    freopen("output2a.txt", "w", stdout);
	init();
	takeInput_andFindFrequency();

	vector<string> nv;
	for(int sn=0;sn<v.size(); sn++){
		string newStr = "";
		for(int i=0;i<v[sn].length(); i++){
			for(int j=0;j<3;j++){
				if(v[sn][i] == char_cnt[j].first){
					newStr += freq[j].first;
					break;
				}
			}
			// substituting the alphabet according to their frequencies and comparing
			// with the original english character frequency table
			if(newStr.length() == i+1) continue;
			if(v[sn][i] == 'g') newStr += 'o';
			else if(v[sn][i] == 'i') newStr += 'h';
			else if(v[sn][i] == 'z') newStr += 'd';
			else if(v[sn][i] == 'o') newStr += 'i';
			else if(v[sn][i] == 'q') newStr += 'k';
			else if(v[sn][i] == 'c') newStr += 'w';
			else if(v[sn][i] == 'j') newStr += 's';
			else if(v[sn][i] == 'f') newStr += 'n';
			else if(v[sn][i] == 'u') newStr += 'g';
			else if(v[sn][i] == 'k') newStr += 'r';
			else if(v[sn][i] == 'e') newStr += 'c';
			else if(v[sn][i] == 'w') newStr += 'u';
			else if(v[sn][i] == 'b') newStr += 'y';
			else if(v[sn][i] == 'y') newStr += 'f';
			else if(v[sn][i] == 's') newStr += 'm';
			else if(v[sn][i] == 'd') newStr += 'l';
			else if(v[sn][i] == 'h') newStr += 'p';
			else if(v[sn][i] == 'r') newStr += 'b';
			else if(v[sn][i] == 'x') newStr += 'v';
			else if(v[sn][i] == 'p') newStr += 'j';
			if(newStr.length() == i) newStr += v[sn][i];
		}
		nv.push_back(newStr);
	}

	cout<<endl;
	for(auto st : nv){
		cout<<st<<' ';
	}
    return 0;
}
