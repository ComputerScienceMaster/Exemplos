; Levy Moreira - 12/04/2012 

#define MyAppName "Program"
#define MyAppVersion "1.0"
#define MyAppPublisher "NextTi"
#define MyAppURL "http://www.nextti.com/"
#define MyAppExeName "Program.jar"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{71419CEF-5E96-4965-81C9-EDC4203BDAB7}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName=C:\Program\
DefaultGroupName={#MyAppName}
OutputDir=D:\Projects\InstallPostgre\output
OutputBaseFilename=SetupProgram
Compression=lzma
SolidCompression=yes

[Languages]
Name: "portuguese"; MessagesFile: "compiler:Languages\Portuguese.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "D:\Projects\InstallPostgre\jars\Program.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Projects\InstallPostgre\jars\lib\*.*"; DestDir:{app}\lib; 
Source: "E:\Projetos\JusHelp\Instalador\postgresql.exe"; DestDir:{tmp}; 


[Run]
