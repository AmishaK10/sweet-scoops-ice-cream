import { defineConfig } from 'vite';\n\nexport default defineConfig({\n  server: {\n    port: 5173,\n    host: true,\n  },\n  preview: {\n    port: 4173,\n    host: true,\n  },\n});
