#include<bits/stdc++.h>
using namespace std;

vector<vector<char> > vigenere(26, vector<char>(26));

void init()
{
	for(int i=0;i<26;i++){
		for(int j=0;j<26;j++){
			int c = 97 + j + i;
			if( c >122) c-=26;
			vigenere[i][j] = (char)c;
			cout<<vigenere[i][j]<<' ';
		}
		cout<<endl;
	}
}
string encrypt()
{
	string s, strInp = "", key;
	// The encyption key.
	cin>>key;  

	// space seperated strings
	while(cin>>s){  
		for(int i=0;i<s.length(); i++){
			if(s[i]>=65 and s[i]<=90) s[i] = tolower(s[i]);
		}
		strInp += s + " ";
	}
	// This will be our cipher text
	string cipher = "";  
	for(int i=0, j=0, n = key.length();i<strInp.length();i++){
		// if it is small letter
		if(strInp[i]>=97 and strInp[i]<=122) { 
			if(j==n) j =0;
			int kc = key[j++] -'a', sc = strInp[i] - 'a';
			// find the particular value from vignere cipher table
			cipher += vigenere[sc][kc]; 
		}
		else cipher += strInp[i];
	}
	cout<<cipher<<endl;
	return "";
}

// string decrypt()
// {
// 	string s, strInp = "", key;
// 	// The decreyption key.
// 	cin>>key;  

// 	// space seperated strings
// 	while(cin>>s){  
// 		for(int i=0;i<s.length(); i++){
// 			if(s[i]>=65 and s[i]<=90) s[i] = tolower(s[i]);
// 		}
// 		strInp += s + " ";
// 	}
// 	// This will be our cipher text
// 	string cipher = "";  
// 	for(int i=0, j=0, n = key.length();i<strInp.length();i++){
// 		// if it is small letter
// 		if(strInp[i]>=97 and strInp[i]<=122) { 
// 			if(j==n) j =0;
// 			int kc = key[j++] -'a', sc = strInp[i] - 'a';
// 			// find the particular value from vignere cipher table
// 			cipher += vigenere[sc][kc]; 
// 		}
// 		else cipher += strInp[i];
// 	}
// 	cout<<cipher<<endl;
// 	return "";
// }
int main()
{
	freopen("input3.txt", "r", stdin);
	freopen("output3.txt", "w", stdout);
	init();
	// int input;
	// cin >> input;
	// switch(input) {
	// case 1:
	// 	encrypt();
	// 	break;
	// case 2:
	// 	decrypt();
	// 	break;
	// default:
	// 	cout<<"error"<<endl<<endl;
	// 	break;
	// }
	encrypt();

	return 0;
}