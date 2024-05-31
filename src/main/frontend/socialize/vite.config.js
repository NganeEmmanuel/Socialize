import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import envCompatible from 'vite-plugin-env-compatible';
import svgrPlugin from 'vite-plugin-svgr';

export default defineConfig({
  envPrefix: 'REACT_APP_',
  build: {
    outDir: 'build',
  },
  plugins: [
    react(),
    envCompatible(),
    svgrPlugin({
      svgrOptions: {
        icon: true,
      },
    }),
  ],
  esbuild: {
    loader: 'jsx', // Corrected the loader configuration
  },
  server: {
    port: 3000, // Change this to your desired port number
  },
});
