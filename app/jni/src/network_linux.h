/*
This file is part of Cuefinger 1

Cuefinger 1 gives you the possibility to remote control Universal Audio's
Console Application via Network (TCP).
Copyright � 2024 Frank Brempel

Cuefinger 1 is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
#ifndef _NETWORK_
#define _NETWORK_

#ifdef __ANDROID__
	#include <SDL.h>
#else
	#include <SDL2/SDL.h>
#endif
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <ifaddrs.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <poll.h>
#include <netdb.h>
#include <string>
#include <unistd.h>
#include <stdexcept>
#include <vector>
#include "translator.h"

using namespace std;

#define TCP_BUFFER_SIZE 512
#define TCP_TIMEOUT 2000

#define MSG_CLIENT_CONNECTED		1
#define MSG_CLIENT_DISCONNECTED		2
#define MSG_CLIENT_CONNECTION_LOST	3
#define MSG_TEXT					4

class TCPClient
{
private:
	int sock;
	SDL_Thread *receiveThreadHandle;
	bool receiveThreadIsRunning;
public:
	TCPClient();
	TCPClient(const string &host, const string &port, void (*MessageCallback)(int,const string&), int timeout = TCP_TIMEOUT); // throws exception
	~TCPClient();
	bool send(const string &data);
	int receive(string &msg, int timeout = TCP_TIMEOUT);

	static bool getClientIPs(vector<string>& ips);
	static string getComputerNameByIP(const string& ip);
	static bool lookUpServers(const string& ipMask, int start, int end, const string& port, int timeout, vector<string>& servers);
private:
	static int SDLCALL receiveThread(void* param);
	void (*MessageCallback)(int msg, const string &data);
	static bool setBlock(int sock, bool block);
	static int connectNonBlock(const string& host, const string& port);
};

#endif