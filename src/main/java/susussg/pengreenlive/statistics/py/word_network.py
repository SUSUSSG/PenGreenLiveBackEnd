import matplotlib.pyplot as plt
from matplotlib import font_manager
from konlpy.tag import Okt
from collections import Counter, defaultdict
import traceback
import networkx as nx
import random
import json
import io
import base64
import sys

def generate_image(review):
    try:
        font_path = 'SCDream5.otf'
        font_prop = font_manager.FontProperties(fname=font_path)
        okt = Okt()
        josa_list = ['은', '는', '이', '가', '을', '를', '에', '의', '와', '과', '도', '으로', '에서', '에게', '한테', '부터', '까지', '마다', '밖에']

        nouns = [noun for noun in okt.nouns(review) if noun not in josa_list]
        if not nouns:
            raise ValueError("No nouns found in the review.")

        counter = Counter(nouns)
        top_nouns = counter.most_common(10)
        if not top_nouns:
            raise ValueError("No top nouns found.")

        words, counts = zip(*top_nouns)

        co_occurrence = defaultdict(int)
        review_nouns = [noun for noun in okt.nouns(review) if noun in words]
        for i in range(len(review_nouns)):
            for j in range(i + 1, len(review_nouns)):
                co_occurrence[(review_nouns[i], review_nouns[j])] += 1

        G = nx.Graph()
        for word, count in top_nouns:
            G.add_node(word, size=count)
        for (word1, word2), freq in co_occurrence.items():
            if freq > 0:
                G.add_edge(word1, word2, weight=freq)

        bright_colors = [
            'aqua', 'coral', 'cornflowerblue', 'darkorange', 'deepskyblue', 'fuchsia',
            'gold', 'hotpink', 'lawngreen', 'limegreen', 'mediumorchid', 'mediumturquoise',
            'orange', 'orangered', 'orchid', 'palevioletred', 'plum', 'salmon', 'springgreen',
            'tomato', 'turquoise', 'violet', 'yellowgreen'
        ]
        random_colors = {word: random.choice(bright_colors) for word in words}

        plt.figure(figsize=(15, 15))
        pos = nx.spring_layout(G, k=0.3)
        nx.draw_networkx_edges(G, pos, alpha=0.3)
        for word, (x, y) in pos.items():
            plt.text(x, y, word, fontsize=G.nodes[word]['size']*40, ha='center', va='center', fontproperties=font_prop, color=random_colors[word])

        plt.axis('off')
        img_buf = io.BytesIO()
        plt.savefig(img_buf, format='png', dpi=300)
        img_buf.seek(0)
        img_base64 = base64.b64encode(img_buf.read()).decode('utf-8')
        img_buf.close()
        return img_base64
    except Exception as e:
         print(json.dumps({"error": str(e)}))
         traceback.print_exc()
         return None

if __name__ == '__main__':
    try:
        review = json.loads(sys.argv[1])
        img_base64 = generate_image(review)
        if img_base64 is not None:
            print(json.dumps({"image": img_base64}))
        else:
            print(json.dumps({"error": "Error generating image"}))
    except Exception as e:
        print(json.dumps({"error": str(e)}))
        traceback.print_exc()
